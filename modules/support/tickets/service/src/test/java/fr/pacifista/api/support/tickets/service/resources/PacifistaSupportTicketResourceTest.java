package fr.pacifista.api.support.tickets.service.resources;

import com.funixproductions.api.google.recaptcha.client.services.GoogleRecaptchaHandler;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.tests.services.ResourceTestHandler;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.support.tickets.client.enums.TicketCreationSource;
import fr.pacifista.api.support.tickets.client.enums.TicketStatus;
import fr.pacifista.api.support.tickets.client.enums.TicketType;
import fr.pacifista.api.support.tickets.service.services.PacifistaSupportTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class PacifistaSupportTicketResourceTest extends ResourceTestHandler {

    @MockBean
    private PacifistaSupportTicketService pacifistaSupportTicketService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockBean
    private GoogleRecaptchaHandler captchaService;

    @BeforeEach
    void setupMocks() {
        doNothing().when(captchaService).verify(any(), any());
        when(pacifistaSupportTicketService.create(any(PacifistaSupportTicketDTO.class)))
                .thenReturn(new PacifistaSupportTicketDTO());
        when(pacifistaSupportTicketService.update(any(PacifistaSupportTicketDTO.class)))
                .thenReturn(new PacifistaSupportTicketDTO());
        when(pacifistaSupportTicketService.getAll(any(), any(), any(), any()))
                .thenReturn(new PageDTO<>());
    }

    @Test
    void testGetAllNormalRefused() throws Exception {
        super.setupNormal();

        mockMvc.perform(get("/support/ticket")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isForbidden());
    }

    @Test
    void testGetAllNoAuthRefused() throws Exception {
        mockMvc.perform(get("/support/ticket")).andExpect(status().isUnauthorized());
    }

    @Test
    void testCreateNormalRefused() throws Exception {
        super.setupNormal();

        mockMvc.perform(post("/support/ticket")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new PacifistaSupportTicketDTO()))
        ).andExpect(status().isForbidden());
    }

    @Test
    void testPatchNormalRefused() throws Exception {
        super.setupNormal();

        mockMvc.perform(patch("/support/ticket")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new PacifistaSupportTicketDTO()))
        ).andExpect(status().isForbidden());
    }

    @Test
    void testGetAllModoSuccess() throws Exception {
        super.setupModerator();

        mockMvc.perform(get("/support/ticket")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isOk());
    }

    @Test
    void testCreateModoSuccess() throws Exception {
        super.setupModerator();

        final PacifistaSupportTicketDTO ticket = new PacifistaSupportTicketDTO();
        ticket.setType(TicketType.OTHER);
        ticket.setCreatedByName("dfff");
        ticket.setCreatedById("dddd");
        ticket.setObject("dfsd");
        ticket.setCreationSource(TicketCreationSource.WEB);
        ticket.setStatus(TicketStatus.CREATED);

        mockMvc.perform(post("/support/ticket")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticket))
        ).andExpect(status().isOk());
    }

    @Test
    void testPatchModoSuccess() throws Exception {
        super.setupModerator();

        mockMvc.perform(patch("/support/ticket")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new PacifistaSupportTicketDTO()))
        ).andExpect(status().isOk());
    }

    @Test
    void testGetAllAdminSuccess() throws Exception {
        super.setupPacifistaAdmin();

        mockMvc.perform(get("/support/ticket")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isOk());
    }

    @Test
    void testCreateAdminSuccess() throws Exception {
        super.setupPacifistaAdmin();

        final PacifistaSupportTicketDTO ticket = new PacifistaSupportTicketDTO();
        ticket.setType(TicketType.OTHER);
        ticket.setCreatedByName("dfff");
        ticket.setCreatedById("dddd");
        ticket.setObject("dfsd");
        ticket.setCreationSource(TicketCreationSource.WEB);
        ticket.setStatus(TicketStatus.CREATED);

        mockMvc.perform(post("/support/ticket")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticket))
        ).andExpect(status().isOk());
    }

    @Test
    void testPatchAdminSuccess() throws Exception {
        super.setupPacifistaAdmin();

        mockMvc.perform(patch("/support/ticket")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new PacifistaSupportTicketDTO()))
        ).andExpect(status().isOk());
    }

    @Test
    void testCreateTicketNoAuthWeb() throws Exception {
        mockMvc.perform(post("/support/ticket/web")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new PacifistaSupportTicketDTO()))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void testCreateTicketUserFromRouteWeb() throws Exception {
        final String clientIp = "8.0.0.1";

        super.setupNormal();
        final PacifistaSupportTicketDTO ticket = new PacifistaSupportTicketDTO();
        ticket.setObject("oui test");
        ticket.setType(TicketType.BUG);
        final UUID token = UUID.randomUUID();

        mockMvc.perform(post("/support/ticket/web")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticket))
                .with(request -> {
                    request.setRemoteAddr(clientIp);
                    return request;
                })
        ).andExpect(status().isOk());

        mockMvc.perform(post("/support/ticket/web")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticket))
                .with(request -> {
                    request.setRemoteAddr(clientIp);
                    return request;
                })
        ).andExpect(status().isForbidden());
    }

    @Test
    void testCreateTicketUserFromRouteWebWithNoObject() throws Exception {
        super.setupNormal();
        final PacifistaSupportTicketDTO ticket = new PacifistaSupportTicketDTO();
        ticket.setType(TicketType.BUG);

        mockMvc.perform(post("/support/ticket/web")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticket))
                .with(request -> {
                    request.setRemoteAddr("8.8.8.8");
                    return request;
                })
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testCreateUserFromRouteWithNoType() throws Exception {
        super.setupNormal();
        final PacifistaSupportTicketDTO ticket = new PacifistaSupportTicketDTO();
        ticket.setObject("oui test");

        mockMvc.perform(post("/support/ticket/web")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticket))
                .with(request -> {
                    request.setRemoteAddr("8.8.8.1");
                    return request;
                })
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testGetUserTickets() throws Exception {
        super.setupNormal();
        final PacifistaSupportTicketDTO ticket = new PacifistaSupportTicketDTO();
        ticket.setObject("oui test");
        ticket.setType(TicketType.BUG);

        mockMvc.perform(post("/support/ticket/web")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticket))
        ).andExpect(status().isOk());

        mockMvc.perform(get("/support/ticket/web")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .with(request -> {
                    request.setRemoteAddr("8.8.8.7");
                    return request;
                })
        ).andExpect(status().isOk());
    }

    @Test
    void testGetUserTicketsWithNoAuth() throws Exception {
        mockMvc.perform(get("/support/ticket/web")).andExpect(status().isUnauthorized());
    }

}
