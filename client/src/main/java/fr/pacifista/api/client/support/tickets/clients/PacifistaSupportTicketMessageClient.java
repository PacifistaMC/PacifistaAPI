package fr.pacifista.api.client.support.tickets.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PacifistaSupportTicketMessage", url = "${pacifista.api.app-domain-url}", path = "/support/ticket/message")
public interface PacifistaSupportTicketMessageClient extends CrudClient<PacifistaSupportTicketMessageDTO> {

    @GetMapping("web")
    PageDTO<PacifistaSupportTicketMessageDTO> fetchUserTicketsMessages(@RequestParam(value = "page", defaultValue = "0") String page,
                                                                       @RequestParam(value = "elemsPerPage", defaultValue = "10") String elemsPerPage,
                                                                       @RequestParam(value = "ticketId") String ticketId);

    @PostMapping("web")
    PacifistaSupportTicketMessageDTO createWeb(@RequestBody PacifistaSupportTicketMessageDTO request);

}
