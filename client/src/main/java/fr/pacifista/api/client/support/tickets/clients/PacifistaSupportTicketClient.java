package fr.pacifista.api.client.support.tickets.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PacifistaSupportTicket", url = "${pacifista.api.app-domain-url}", path = "/support/ticket")
public interface PacifistaSupportTicketClient extends CrudClient<PacifistaSupportTicketDTO> {
}
