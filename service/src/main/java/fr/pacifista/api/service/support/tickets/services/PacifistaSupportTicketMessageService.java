package fr.pacifista.api.service.support.tickets.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketMessageDTO;
import fr.pacifista.api.service.support.tickets.entities.PacifistaSupportTicket;
import fr.pacifista.api.service.support.tickets.entities.PacifistaSupportTicketMessage;
import fr.pacifista.api.service.support.tickets.mappers.PacifistaSupportTicketMessageMapper;
import fr.pacifista.api.service.support.tickets.repositories.PacifistaSupportTicketMessageRepository;
import fr.pacifista.api.service.support.tickets.repositories.PacifistaSupportTicketRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacifistaSupportTicketMessageService extends ApiService<PacifistaSupportTicketMessageDTO, PacifistaSupportTicketMessage, PacifistaSupportTicketMessageMapper, PacifistaSupportTicketMessageRepository> {

    private final PacifistaSupportTicketRepository ticketRepository;
    private final PacifistaSupportWebSocketTicketMessageService webSocketTicketMessageService;

    public PacifistaSupportTicketMessageService(PacifistaSupportTicketMessageRepository repository,
                                                PacifistaSupportTicketMessageMapper mapper,
                                                PacifistaSupportTicketRepository ticketRepository,
                                                PacifistaSupportWebSocketTicketMessageService webSocketTicketMessageService) {
        super(repository, mapper);
        this.ticketRepository = ticketRepository;
        this.webSocketTicketMessageService = webSocketTicketMessageService;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PacifistaSupportTicketMessage> entity) {
        for (final PacifistaSupportTicketMessage message : entity) {
            if (message.getId() != null) {
                throw new ApiBadRequestException("Les messages ne peuvent être modifiés");
            }

            if (message.getTicket() == null || message.getTicket().getUuid() == null) {
                throw new ApiBadRequestException("Le ticket id est obligatoire");
            } else {
                final Optional<PacifistaSupportTicket> ticketSearch = this.ticketRepository.findByUuid(message.getTicket().getUuid().toString());
                if (ticketSearch.isEmpty()) {
                    throw new ApiNotFoundException("Le ticket spécifié n'existe pas");
                } else {
                    message.setTicket(ticketSearch.get());
                }
            }
        }
    }

    @Override
    public void afterSavingEntity(@NonNull Iterable<PacifistaSupportTicketMessage> entity) {
        for (final PacifistaSupportTicketMessage message : entity) {
            this.webSocketTicketMessageService.newTicketMessage(this.getMapper().toDto(message));
        }
    }

}
