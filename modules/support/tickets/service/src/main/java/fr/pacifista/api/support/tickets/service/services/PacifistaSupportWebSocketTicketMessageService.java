package fr.pacifista.api.support.tickets.service.services;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.tools.websocket.services.ApiWebsocketServerHandler;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import feign.FeignException;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketMessageDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PacifistaSupportWebSocketTicketMessageService extends ApiWebsocketServerHandler {

    private static final String SUBSCRIBE_CALL_CLIENT = "subscribe-channel";
    private static final String MESSAGE_EVENT = "ticket-message-event";
    private static final String AUTH_REQUEST = "pass-bearer-token";

    private final Map<String, String> ticketsMessagingSubscriptions = new HashMap<>();
    private final Map<String, UserDTO> authSessions = new HashMap<>();
    private final Gson gson = new Gson();

    private final UserAuthClient authClient;
    private final PacifistaSupportTicketService ticketService;

    public PacifistaSupportWebSocketTicketMessageService(final UserAuthClient authClient,
                                                         final PacifistaSupportTicketService ticketService) {
        this.authClient = authClient;
        this.ticketService = ticketService;
    }

    public void newTicketMessage(final PacifistaSupportTicketMessageDTO message) {
        final String ticketId = message.getTicket().getId().toString();

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
        }
    }

    @Override
    protected void onClientDisconnect(String sessionId) {
        this.ticketsMessagingSubscriptions.remove(sessionId);
        this.authSessions.remove(sessionId);
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
