package fr.pacifista.api.service.support.tickets.resources;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.funixgaming.api.client.user.dtos.UserDTO;
import fr.funixgaming.api.core.crud.dtos.PageDTO;
import fr.funixgaming.api.core.crud.enums.SearchOperation;
import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.funixgaming.api.core.exceptions.ApiForbiddenException;
import fr.funixgaming.api.core.external.google.captcha.services.GoogleCaptchaService;
import fr.pacifista.api.client.support.tickets.clients.PacifistaSupportTicketMessageClient;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketMessageDTO;
import fr.pacifista.api.service.core.auth.entities.Session;
import fr.pacifista.api.service.core.auth.services.ActualSession;
import fr.pacifista.api.service.support.tickets.services.PacifistaSupportTicketMessageService;
import fr.pacifista.api.service.support.tickets.services.PacifistaSupportTicketService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("support/ticket/message")
public class PacifistaSupportTicketMessageResource extends ApiResource<PacifistaSupportTicketMessageDTO, PacifistaSupportTicketMessageService> implements PacifistaSupportTicketMessageClient {

    private final ActualSession actualSession;
    private final GoogleCaptchaService googleCaptchaService;
    private final PacifistaSupportTicketService ticketService;

    private final Cache<String, Instant> ticketCreationCache = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.SECONDS).build();

    public PacifistaSupportTicketMessageResource(PacifistaSupportTicketMessageService pacifistaSupportTicketMessageService,
                                                 ActualSession actualSession,
                                                 GoogleCaptchaService googleCaptchaService,
                                                 PacifistaSupportTicketService ticketService) {
        super(pacifistaSupportTicketMessageService);
        this.actualSession = actualSession;
        this.googleCaptchaService = googleCaptchaService;
        this.ticketService = ticketService;
    }

    @Override
    public PageDTO<PacifistaSupportTicketMessageDTO> fetchUserTicketsMessages(String page, String elemsPerPage, String ticketId) {
        final Session session = actualSession.getActualSession();
        if (session == null) {
            throw new ApiForbiddenException("Vous devez être connecté pour voir vos tickets");
        }

        final UserDTO userDTO = session.getUser();
        final PacifistaSupportTicketDTO ticketDTO = ticketService.findById(ticketId);
        if (!ticketDTO.getCreatedById().equals(userDTO.getId().toString())) {
            throw new ApiForbiddenException("Vous ne pouvez pas voir les messages d'un ticket qui ne vous appartient pas");
        }

        return super.getAll(
                page,
                elemsPerPage,
                String.format("ticket.createdById:%s:%s", SearchOperation.EQUALS.getOperation(), userDTO.getId()) + ',' +
                String.format("ticket.uuid:%s:%s", SearchOperation.EQUALS.getOperation(), ticketId),
                "createdAt:desc"
        );
    }

    @Override
    public PacifistaSupportTicketMessageDTO createWeb(PacifistaSupportTicketMessageDTO request) {
        final Session session = actualSession.getActualSession();
        if (session == null) {
            throw new ApiForbiddenException("Vous devez être connecté pour répondre à un ticket");
        }
        if (Strings.isNullOrEmpty(request.getMessage())) {
            throw new ApiBadRequestException("Le message ne peut pas être vide");
        }

        this.checkSpam(session);
        this.googleCaptchaService.checkCode(session.getRequest(), "pacifista-support-ticket-message");

        final UserDTO userDTO = session.getUser();
        request.setWrittenByName(userDTO.getUsername());
        request.setWrittenById(userDTO.getId().toString());

        final PacifistaSupportTicketMessageDTO ticketMessage = super.create(request);
        this.ticketCreationCache.put(session.getClientIp(), Instant.now());
        return ticketMessage;
    }

    private void checkSpam(final @NonNull Session session) {
        if (this.ticketCreationCache.getIfPresent(session.getClientIp()) != null) {
            throw new ApiForbiddenException("Veuillez patienter 5 secondes entre chaque réponse à un ticket");
        }
    }
}
