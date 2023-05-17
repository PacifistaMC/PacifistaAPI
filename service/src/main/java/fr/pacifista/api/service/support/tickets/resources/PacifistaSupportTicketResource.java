package fr.pacifista.api.service.support.tickets.resources;

import com.funixproductions.api.client.user.dtos.UserDTO;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.crud.resources.ApiResource;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiForbiddenException;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.pacifista.api.client.support.tickets.clients.PacifistaSupportTicketClient;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.client.support.tickets.enums.TicketCreationSource;
import fr.pacifista.api.client.support.tickets.enums.TicketStatus;
import fr.pacifista.api.service.core.auth.entities.Session;
import fr.pacifista.api.service.core.auth.services.ActualSession;
import fr.pacifista.api.service.google.recaptcha.GoogleCaptchaService;
import fr.pacifista.api.service.support.tickets.services.PacifistaSupportTicketService;
import fr.pacifista.api.service.support.tickets.services.PacifistaSupportWebSocketTicketMessageService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("support/ticket")
public class PacifistaSupportTicketResource extends ApiResource<PacifistaSupportTicketDTO, PacifistaSupportTicketService> implements PacifistaSupportTicketClient {

    private final ActualSession actualSession;
    private final GoogleCaptchaService googleCaptchaService;
    private final Cache<String, Instant> ticketCreationCache = CacheBuilder.newBuilder()
            .expireAfterAccess(3, TimeUnit.MINUTES).build();

    private final PacifistaSupportWebSocketTicketMessageService webSocket;

    public PacifistaSupportTicketResource(PacifistaSupportTicketService pacifistaSupportTicketService,
                                          ActualSession actualSession,
                                          GoogleCaptchaService googleCaptchaService,
                                          PacifistaSupportWebSocketTicketMessageService webSocket) {
        super(pacifistaSupportTicketService);
        this.actualSession = actualSession;
        this.googleCaptchaService = googleCaptchaService;
        this.webSocket = webSocket;
    }

    @Override
    public PageDTO<PacifistaSupportTicketDTO> fetchUserTickets(String page, String elemsPerPage, String ticketStatus) {
        final Session session = actualSession.getActualSession();
        if (session == null) {
            throw new ApiForbiddenException("Vous devez être connecté pour voir vos tickets");
        }
        final String createdBySearch = String.format("createdById:%s:%s", SearchOperation.EQUALS.getOperation(), session.getUser().getId());
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
        final Session session = actualSession.getActualSession();
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
        this.googleCaptchaService.checkCode(session.getRequest(), "pacifistasupportticketcreate");

        final UserDTO userDTO = session.getUser();
        request.setCreatedByName(userDTO.getUsername());
        request.setCreatedById(userDTO.getId().toString());
        request.setCreationSource(TicketCreationSource.WEB);
        request.setStatus(TicketStatus.CREATED);

        final PacifistaSupportTicketDTO ticket = super.create(request);
        this.ticketCreationCache.put(session.getClientIp(), Instant.now());
        return ticket;
    }

    private void checkSpam(final @NonNull Session session) {
        if (this.ticketCreationCache.getIfPresent(session.getClientIp()) != null) {
            throw new ApiForbiddenException("Vous avez déjà créé un ticket il y a moins de 3 minutes");
        }
    }
}
