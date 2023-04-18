package fr.pacifista.api.client.support.tickets.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PacifistaSupportTicketMessage", url = "${pacifista.api.app-domain-url}", path = "/support/ticket/message")
public interface PacifistaSupportTicketMessageClient extends CrudClient<PacifistaSupportTicketMessageDTO> {
}
