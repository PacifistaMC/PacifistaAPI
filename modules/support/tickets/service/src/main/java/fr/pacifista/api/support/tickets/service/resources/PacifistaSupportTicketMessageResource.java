package fr.pacifista.api.support.tickets.service.resources;

import com.funixproductions.api.google.recaptcha.client.services.GoogleRecaptchaHandler;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.dtos.UserSession;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.crud.resources.ApiResource;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiForbiddenException;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.pacifista.api.support.tickets.client.clients.PacifistaSupportTicketMessageClient;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketMessageDTO;
import fr.pacifista.api.support.tickets.service.services.PacifistaSupportTicketMessageService;
import fr.pacifista.api.support.tickets.service.services.PacifistaSupportTicketService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("support/ticket/message")
public class PacifistaSupportTicketMessageResource extends ApiResource<PacifistaSupportTicketMessageDTO, PacifistaSupportTicketMessageService> implements PacifistaSupportTicketMessageClient {

    private final CurrentSession actualSession;
    private final GoogleRecaptchaHandler googleCaptchaService;
    private final PacifistaSupportTicketService ticketService;

    private final Cache<String, Instant> ticketCreationCache = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.SECONDS).build();

    public PacifistaSupportTicketMessageResource(PacifistaSupportTicketMessageService pacifistaSupportTicketMessageService,
                                                 CurrentSession actualSession,
                                                 GoogleRecaptchaHandler googleCaptchaService,
                                                 PacifistaSupportTicketService ticketService) {
        super(pacifistaSupportTicketMessageService);
        this.actualSession = actualSession;
        this.googleCaptchaService = googleCaptchaService;
        this.ticketService = ticketService;
    }

    @Override
    public PageDTO<PacifistaSupportTicketMessageDTO> fetchUserTicketsMessages(String page, String elemsPerPage, String ticketId) {
        final UserSession session = actualSession.getUserSession();
        if (session == null) {
            throw new ApiForbiddenException("Vous devez être connecté pour voir vos tickets");
        }

        final UserDTO userDTO = session.getUserDTO();
        final PacifistaSupportTicketDTO ticketDTO = ticketService.findById(ticketId);
        if (!ticketDTO.getCreatedById().equals(userDTO.getId().toString())) {
            throw new ApiForbiddenException("Vous ne pouvez pas voir les messages d'un ticket qui ne vous appartient pas");
        }

        return super.getAll(
                page,
                elemsPerPage,
                String.format("ticket.createdById:%s:%s", SearchOperation.EQUALS.getOperation(), userDTO.getId()) + ',' +
                String.format("ticket.uuid:%s:%s", SearchOperation.EQUALS.getOperation(), ticketId),
                "createdAt:asc"
        );
    }

    @Override
    public PacifistaSupportTicketMessageDTO createWeb(PacifistaSupportTicketMessageDTO request) {
        final UserSession session = actualSession.getUserSession();
        if (session == null) {
            throw new ApiForbiddenException("Vous devez être connecté pour répondre à un ticket");
        }
        if (Strings.isNullOrEmpty(request.getMessage())) {
            throw new ApiBadRequestException("Le message ne peut pas être vide");
        }

        this.checkSpam(session);
        this.googleCaptchaService.verify(session.getRequest(), "pacifistasupportticketmessage");

        final UserDTO userDTO = session.getUserDTO();
        request.setWrittenByName(userDTO.getUsername());
        request.setWrittenById(userDTO.getId().toString());

        final PacifistaSupportTicketMessageDTO ticketMessage = super.create(request);
        this.ticketCreationCache.put(session.getIp(), Instant.now());
        return ticketMessage;
    }

    private void checkSpam(final @NonNull UserSession session) {
        if (this.ticketCreationCache.getIfPresent(session.getIp()) != null) {
            throw new ApiForbiddenException("Veuillez patienter 5 secondes entre chaque réponse à un ticket");
        }
    }
}
