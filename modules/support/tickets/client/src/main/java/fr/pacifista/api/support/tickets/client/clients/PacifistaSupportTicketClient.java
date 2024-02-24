package fr.pacifista.api.support.tickets.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "PacifistaSupportTicket",
        url = "${pacifista.api.app-domain-url}",
        path = "/support/ticket",
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaSupportTicketClient extends CrudClient<PacifistaSupportTicketDTO> {

    @GetMapping("web")
    PageDTO<PacifistaSupportTicketDTO> fetchUserTickets(@RequestParam(value = "page", defaultValue = "0") String page,
                                                        @RequestParam(value = "elemsPerPage", defaultValue = "10") String elemsPerPage,
                                                        @RequestParam(value = "ticketStatus", defaultValue = "all") String ticketStatus);

    @PostMapping("web")
    PacifistaSupportTicketDTO createTicketFromWeb(@RequestBody PacifistaSupportTicketDTO request);

}
