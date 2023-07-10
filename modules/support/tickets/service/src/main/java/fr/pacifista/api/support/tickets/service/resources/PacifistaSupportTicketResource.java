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
import fr.pacifista.api.support.tickets.client.clients.PacifistaSupportTicketClient;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.support.tickets.client.enums.TicketCreationSource;
import fr.pacifista.api.support.tickets.client.enums.TicketStatus;
import fr.pacifista.api.support.tickets.service.services.PacifistaSupportTicketService;
import fr.pacifista.api.support.tickets.service.services.PacifistaSupportWebSocketTicketMessageService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("support/ticket")
public class PacifistaSupportTicketResource extends ApiResource<PacifistaSupportTicketDTO, PacifistaSupportTicketService> implements PacifistaSupportTicketClient {

    private final CurrentSession actualSession;
    private final GoogleRecaptchaHandler googleCaptchaService;
    private final Cache<String, Instant> ticketCreationCache = CacheBuilder.newBuilder()
            .expireAfterAccess(3, TimeUnit.MINUTES).build();

    private final PacifistaSupportWebSocketTicketMessageService webSocket;

    public PacifistaSupportTicketResource(PacifistaSupportTicketService pacifistaSupportTicketService,
                                          CurrentSession actualSession,
                                          GoogleRecaptchaHandler googleCaptchaService,
                                          PacifistaSupportWebSocketTicketMessageService webSocket) {
        super(pacifistaSupportTicketService);
        this.actualSession = actualSession;
        this.googleCaptchaService = googleCaptchaService;
        this.webSocket = webSocket;
    }

    @Override
    public PageDTO<PacifistaSupportTicketDTO> fetchUserTickets(String page, String elemsPerPage, String ticketStatus) {
        final UserSession session = actualSession.getUserSession();
        if (session == null) {
            throw new ApiForbiddenException("Vous devez être connecté pour voir vos tickets");
        }
        final String createdBySearch = String.format("createdById:%s:%s", SearchOperation.EQUALS.getOperation(), session.getUserDTO().getId());
        final String statusSearch = Strings.isNullOrEmpty(ticketStatus) || ticketStatus.equalsIgnoreCase("all") ? "" :
                String.format("status:%s:%s", SearchOperation.EQUALS.getOperation(), ticketStatus);

        return super.getService().getAll(
                page, elemsPerPage,
                statusSearch + (Strings.isNullOrEmpty(statusSearch) ? "" : ",") + createdBySearch,
                "createdAt:desc"
        );
    }

    @Override
    public PacifistaSupportTicketDTO createTicketFromWeb(PacifistaSupportTicketDTO request) {
        final UserSession session = actualSession.getUserSession();
        if (session == null) {
            throw new ApiForbiddenException("Vous devez être connecté pour créer un ticket");
        }
        if (Strings.isNullOrEmpty(request.getObject())) {
            throw new ApiBadRequestException("L'objet du ticket ne peut pas être vide");
        }
        if (request.getType() == null) {
            throw new ApiBadRequestException("Le type du ticket ne peut pas être vide");
        }

        this.checkSpam(session);
        this.googleCaptchaService.verify(session.getRequest(), "pacifistasupportticketcreate");

        final UserDTO userDTO = session.getUserDTO();
        request.setCreatedByName(userDTO.getUsername());
        request.setCreatedById(userDTO.getId().toString());
        request.setCreationSource(TicketCreationSource.WEB);
        request.setStatus(TicketStatus.CREATED);

        final PacifistaSupportTicketDTO ticket = super.create(request);
        this.ticketCreationCache.put(session.getIp(), Instant.now());
        return ticket;
    }

    private void checkSpam(final @NonNull UserSession session) {
        if (this.ticketCreationCache.getIfPresent(session.getIp()) != null) {
            throw new ApiForbiddenException("Vous avez déjà créé un ticket il y a moins de 3 minutes");
        }
    }
}
