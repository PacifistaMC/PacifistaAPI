package fr.pacifista.api.support.tickets.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.support.tickets.service.entities.PacifistaSupportTicketMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaSupportTicketMessageRepository extends ApiRepository<PacifistaSupportTicketMessage> {
}
