package fr.pacifista.api.service.support.tickets.services;

import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.client.support.tickets.enums.TicketCreationSource;
import fr.pacifista.api.client.support.tickets.enums.TicketStatus;
import fr.pacifista.api.client.support.tickets.enums.TicketType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PacifistaSupportTicketServiceTest {

    @Autowired
    private PacifistaSupportTicketService pacifistaSupportTicketService;

    @Test
    void createEntity() {
        final PacifistaSupportTicketDTO ticketDTO = new PacifistaSupportTicketDTO();
        ticketDTO.setObject("Test" + UUID.randomUUID());
        ticketDTO.setCreatedByName("Test" + UUID.randomUUID());
        ticketDTO.setCreatedById("Test" + UUID.randomUUID());
        ticketDTO.setCreationSource(TicketCreationSource.WEB);
        ticketDTO.setType(TicketType.OTHER);
        ticketDTO.setStatus(TicketStatus.CREATED);

        assertDoesNotThrow(() -> {
            final PacifistaSupportTicketDTO createdTicket = pacifistaSupportTicketService.create(ticketDTO);
            assertEquals(ticketDTO.getObject(), createdTicket.getObject());
            assertEquals(ticketDTO.getCreatedByName(), createdTicket.getCreatedByName());
            assertEquals(ticketDTO.getCreatedById(), createdTicket.getCreatedById());
            assertEquals(ticketDTO.getCreationSource(), createdTicket.getCreationSource());
            assertEquals(ticketDTO.getType(), createdTicket.getType());
            assertEquals(ticketDTO.getStatus(), createdTicket.getStatus());
        });
    }

    @Test
    void testPatch() {
        final PacifistaSupportTicketDTO ticketDTO = new PacifistaSupportTicketDTO();
        ticketDTO.setObject("Test" + UUID.randomUUID());
        ticketDTO.setCreatedByName("Test" + UUID.randomUUID());
        ticketDTO.setCreatedById("Test" + UUID.randomUUID());
        ticketDTO.setCreationSource(TicketCreationSource.WEB);
        ticketDTO.setType(TicketType.OTHER);
        ticketDTO.setStatus(TicketStatus.CREATED);

        assertDoesNotThrow(() -> {
            final PacifistaSupportTicketDTO createdTicket = pacifistaSupportTicketService.create(ticketDTO);
            createdTicket.setType(TicketType.BUG);

            final PacifistaSupportTicketDTO patchedTicket = pacifistaSupportTicketService.update(createdTicket);
            assertEquals(createdTicket.getType(), patchedTicket.getType());
        });
    }

}
