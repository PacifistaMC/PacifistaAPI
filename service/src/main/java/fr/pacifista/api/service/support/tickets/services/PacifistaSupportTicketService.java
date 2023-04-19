package fr.pacifista.api.service.support.tickets.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.service.support.tickets.entities.PacifistaSupportTicket;
import fr.pacifista.api.service.support.tickets.mappers.PacifistaSupportTicketMapper;
import fr.pacifista.api.service.support.tickets.repositories.PacifistaSupportTicketRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaSupportTicketService extends ApiService<PacifistaSupportTicketDTO, PacifistaSupportTicket, PacifistaSupportTicketMapper, PacifistaSupportTicketRepository> {

    public PacifistaSupportTicketService(PacifistaSupportTicketRepository repository,
                                         PacifistaSupportTicketMapper mapper) {
        super(repository, mapper);
    }

}
