package fr.pacifista.api.client.support.tickets.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.funixgaming.api.core.crud.dtos.PageDTO;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PacifistaSupportTicket", url = "${pacifista.api.app-domain-url}", path = "/support/ticket")
public interface PacifistaSupportTicketClient extends CrudClient<PacifistaSupportTicketDTO> {

    @GetMapping("web")
    PageDTO<PacifistaSupportTicketDTO> fetchUserTickets(@RequestParam(value = "page", defaultValue = "0") String page,
                                                        @RequestParam(value = "elemsPerPage", defaultValue = "10") String elemsPerPage);

    @PostMapping("web")
    PacifistaSupportTicketDTO createTicketFromWeb(@RequestBody PacifistaSupportTicketDTO request);

}
