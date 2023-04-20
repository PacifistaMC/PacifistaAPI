package fr.pacifista.api.service.support.tickets.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.service.support.tickets.entities.PacifistaSupportTicketMessage;
import org.springframework.stereotype.Repository;

@Repository
public interface PacifistaSupportTicketMessageRepository extends ApiRepository<PacifistaSupportTicketMessage> {
}
