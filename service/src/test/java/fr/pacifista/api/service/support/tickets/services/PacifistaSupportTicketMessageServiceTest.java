package fr.pacifista.api.service.support.tickets.services;

import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketMessageDTO;
import fr.pacifista.api.client.support.tickets.enums.TicketCreationSource;
import fr.pacifista.api.client.support.tickets.enums.TicketStatus;
import fr.pacifista.api.client.support.tickets.enums.TicketType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacifistaSupportTicketMessageServiceTest {

    @Autowired
    private PacifistaSupportTicketMessageService ticketMessageService;

    @Autowired
    private PacifistaSupportTicketService ticketService;

    @Test
    void createEntity() {
        final PacifistaSupportTicketMessageDTO dto = new PacifistaSupportTicketMessageDTO();
        dto.setTicket(generateTicket());
        dto.setWrittenByName("Test" + UUID.randomUUID());
        dto.setWrittenById("Test" + UUID.randomUUID());
        dto.setMessage("Test" + UUID.randomUUID());

        assertDoesNotThrow(() -> {
            final PacifistaSupportTicketMessageDTO created = ticketMessageService.create(dto);
            assertEquals(dto.getMessage(), created.getMessage());
            assertEquals(dto.getWrittenByName(), created.getWrittenByName());
            assertEquals(dto.getWrittenById(), created.getWrittenById());
            assertEquals(dto.getTicket(), created.getTicket());
        });
    }

    @Test
    void patchEntity() {
        final PacifistaSupportTicketMessageDTO dto = new PacifistaSupportTicketMessageDTO();
        dto.setTicket(generateTicket());
        dto.setWrittenByName("Test" + UUID.randomUUID());
        dto.setWrittenById("Test" + UUID.randomUUID());
        dto.setMessage("Test" + UUID.randomUUID());

        final PacifistaSupportTicketMessageDTO created = ticketMessageService.create(dto);
        created.setMessage("changed");
        assertThrowsExactly(ApiBadRequestException.class, () -> ticketMessageService.update(created));
    }

    private PacifistaSupportTicketDTO generateTicket() {
        final PacifistaSupportTicketDTO ticketDTO = new PacifistaSupportTicketDTO();
        ticketDTO.setObject("Test" + UUID.randomUUID());
        ticketDTO.setCreatedByName("Test" + UUID.randomUUID());
        ticketDTO.setCreatedById("Test" + UUID.randomUUID());
        ticketDTO.setCreationSource(TicketCreationSource.WEB);
        ticketDTO.setType(TicketType.OTHER);
        ticketDTO.setStatus(TicketStatus.CREATED);

        return ticketService.create(ticketDTO);
    }

}
