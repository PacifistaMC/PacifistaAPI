package fr.pacifista.api.service.support.tickets.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.support.tickets.entities.PacifistaSupportTicketMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaSupportTicketMessageRepository extends ApiRepository<PacifistaSupportTicketMessage> {
}
