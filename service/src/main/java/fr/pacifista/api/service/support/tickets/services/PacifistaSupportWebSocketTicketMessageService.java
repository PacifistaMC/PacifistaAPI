package fr.pacifista.api.service.support.tickets.services;

import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import feign.FeignException;
import fr.funixgaming.api.client.user.clients.UserAuthClient;
import fr.funixgaming.api.client.user.dtos.UserDTO;
import fr.funixgaming.api.client.user.enums.UserRole;
import fr.funixgaming.api.core.exceptions.ApiException;
import fr.funixgaming.api.core.utils.websocket.services.ApiWebsocketServerHandler;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketMessageDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class PacifistaSupportWebSocketTicketMessageService extends ApiWebsocketServerHandler {

    private static final String SUBSCRIBE_CALL_CLIENT = "subscribe-channel";
    private static final String MESSAGE_EVENT = "ticket-message-event";
    private static final String AUTH_REQUEST = "pass-bearer-token";

    private final Map<String, String> ticketsMessagingSubscriptions = new HashMap<>();
    private final Map<String, UserDTO> authSessions = new HashMap<>();
    private final Set<String> fcmMap = new HashSet<>();
    private final Gson gson = new Gson();

    private final UserAuthClient authClient;
    private final PacifistaSupportTicketService ticketService;
    private final REMOVETHIS fcmService;

    public PacifistaSupportWebSocketTicketMessageService(final UserAuthClient authClient,
                                                         final PacifistaSupportTicketService ticketService,
                                                         final REMOVETHIS fcmService) {
        this.authClient = authClient;
        this.ticketService = ticketService;
        this.fcmService = fcmService;
    }

    public void newTicketMessage(final PacifistaSupportTicketMessageDTO message) {
        final String ticketId = message.getTicket().getId().toString();

        this.remove(message.getTicket());
        for (final Map.Entry<String, String> entry : this.ticketsMessagingSubscriptions.entrySet()) {
            final String sessionId = entry.getKey();
            final String ticketIdEntry = entry.getValue();

            if (ticketIdEntry.equals(ticketId)) {
                try {
                    super.sendMessageToSessionId(sessionId, MESSAGE_EVENT + ":" + gson.toJson(message));
                } catch (ApiException e) {
                    log.error("Impossible d'envoyer un message websocket à la session id {}.", sessionId, e);
                }
            }
        }
    }

    void remove(final PacifistaSupportTicketDTO ticket) {
        for (final String tokenFcm : this.fcmMap) {
            if (!Strings.isNullOrEmpty(tokenFcm)) {
                final String title = "Nouveau message sur le ticket #" + ticket.getId();
                final String body = "Un nouveau message a été posté sur le ticket #" + ticket.getId() + " clickez pour en savoir +.";
                final String url = "https://dashboard.funixproductions.com/dashboard/pacifista/tickets/messages/" + ticket.getId();
                final REMOVEDTO.Notification notification = new REMOVEDTO.Notification();
                notification.setTitle(title);
                notification.setBody(body);
                notification.setUrl(url);
                final REMOVEDTO dto = new REMOVEDTO();
                dto.setTo(tokenFcm);
                dto.setNotification(notification);
                this.fcmService.sendNotification("key=" + System.getenv("FCM_SERVER_KEY"), dto);
            }
        }
    }

    @Override
    protected void newWebsocketMessage(@NonNull WebSocketSession session, @NonNull String message) throws Exception {
        final String[] data = message.split(":");

        if (message.startsWith(SUBSCRIBE_CALL_CLIENT) && data.length == 2) {
            final String ticketId = data[1];

            if (!Strings.isNullOrEmpty(ticketId) && canListenToThatTicket(ticketId, session.getId())) {
                this.ticketsMessagingSubscriptions.put(session.getId(), ticketId);
            }
        } else if (message.startsWith(AUTH_REQUEST) && data.length == 2) {
            final String token = data[1];

            if (!Strings.isNullOrEmpty(token)) {
                try {
                    final UserDTO userDTO = this.authClient.current(token);
                    this.authSessions.put(session.getId(), userDTO);
                } catch (FeignException e) {
                    log.error("Impossible de récupérer l'utilisateur à partir du token.", e);
                }
            }
        } else if (message.startsWith("fcm-token") && data.length == 2) {
            final String fcmToken = data[1];

            if (!Strings.isNullOrEmpty(fcmToken)) {
                this.fcmMap.add(fcmToken);
            }
        }
    }

    @Override
    protected void onClientDisconnect(String sessionId) {
        this.ticketsMessagingSubscriptions.remove(sessionId);
        this.authSessions.remove(sessionId);
        this.fcmMap.remove(sessionId);
    }

    private boolean canListenToThatTicket(final String ticketId, final String sessionId) {
        final UserDTO actualUser = this.authSessions.get(sessionId);

        if (actualUser == null) {
            return false;
        } else {
            try {
                if (actualUser.getRole().equals(UserRole.PACIFISTA_ADMIN) ||
                        actualUser.getRole().equals(UserRole.PACIFISTA_MODERATOR) ||
                        actualUser.getRole().equals(UserRole.ADMIN)) {
                    return true;
                } else {
                    return isUserOwnTicket(actualUser, ticketId);
                }
            } catch (FeignException e) {
                return false;
            }
        }
    }

    private boolean isUserOwnTicket(final UserDTO actualUser, final String ticketId) {
        try {
            final PacifistaSupportTicketDTO ticketDTO = this.ticketService.findById(ticketId);
            return ticketDTO.getCreatedById().equals(actualUser.getId().toString());
        } catch (ApiException e) {
            return false;
        }
    }
}
