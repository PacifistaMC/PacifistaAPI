package fr.pacifista.api.support.tickets.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.support.tickets.service.entities.PacifistaSupportTicket;
import fr.pacifista.api.support.tickets.service.mappers.PacifistaSupportTicketMapper;
import fr.pacifista.api.support.tickets.service.repositories.PacifistaSupportTicketRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaSupportTicketService extends ApiService<PacifistaSupportTicketDTO, PacifistaSupportTicket, PacifistaSupportTicketMapper, PacifistaSupportTicketRepository> {

    public PacifistaSupportTicketService(PacifistaSupportTicketRepository repository,
                                         PacifistaSupportTicketMapper mapper) {
        super(repository, mapper);
    }

}
