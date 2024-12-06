package fr.pacifista.api.support.tickets.service.resources;

import com.funixproductions.api.google.recaptcha.client.services.GoogleRecaptchaHandler;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.tests.services.ResourceTestHandler;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketMessageDTO;
import fr.pacifista.api.support.tickets.service.services.PacifistaSupportTicketMessageService;
import fr.pacifista.api.support.tickets.service.services.PacifistaSupportTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class PacifistaSupportTicketMessageResourceTest extends ResourceTestHandler {

    @MockitoBean
    private PacifistaSupportTicketService pacifistaSupportTicketService;

    @MockitoBean
    private PacifistaSupportTicketMessageService pacifistaSupportTicketMessageService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockitoBean
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
        when(pacifistaSupportTicketMessageService.create(any(PacifistaSupportTicketMessageDTO.class)))
                .thenReturn(new PacifistaSupportTicketMessageDTO());
        when(pacifistaSupportTicketMessageService.update(any(PacifistaSupportTicketMessageDTO.class)))
                .thenReturn(new PacifistaSupportTicketMessageDTO());
        when(pacifistaSupportTicketMessageService.getAll(any(), any(), any(), any()))
                .thenReturn(new PageDTO<>());
        final PacifistaSupportTicketDTO ticketDTO = new PacifistaSupportTicketDTO();
        ticketDTO.setCreatedByName("name");
        ticketDTO.setCreatedById("id");
        when(pacifistaSupportTicketService.findById(any())).thenReturn(ticketDTO);
        when(pacifistaSupportTicketMessageService.findById(any())).thenReturn(new PacifistaSupportTicketMessageDTO());
    }

    @Test
    void testGetAllNormalRefused() throws Exception {
        super.setupNormal();

        mockMvc.perform(get("/support/ticket/message")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isForbidden());
    }

    @Test
    void testGetAllNoAuthRefused() throws Exception {
        mockMvc.perform(get("/support/ticket/message")).andExpect(status().isUnauthorized());
    }

    @Test
    void testCreateNormalRefused() throws Exception {
        super.setupNormal();

        mockMvc.perform(post("/support/ticket/message")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new PacifistaSupportTicketMessageDTO()))
        ).andExpect(status().isForbidden());
    }

    @Test
    void testPatchNormalRefused() throws Exception {
        super.setupNormal();

        mockMvc.perform(patch("/support/ticket/message")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new PacifistaSupportTicketMessageDTO()))
        ).andExpect(status().isForbidden());
    }

    @Test
    void testGetAllModoSuccess() throws Exception {
        super.setupModerator();

        mockMvc.perform(get("/support/ticket/message")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isOk());
    }

    @Test
    void testCreateModoSuccess() throws Exception {
        super.setupModerator();

        final PacifistaSupportTicketMessageDTO ticketMessageDTO = new PacifistaSupportTicketMessageDTO();
        ticketMessageDTO.setTicket(new PacifistaSupportTicketDTO());
        ticketMessageDTO.setMessage("message" + UUID.randomUUID());
        ticketMessageDTO.setWrittenByName("yolo");
        ticketMessageDTO.setWrittenById(UUID.randomUUID().toString());

        mockMvc.perform(post("/support/ticket/message")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticketMessageDTO))
        ).andExpect(status().isOk());
    }

    @Test
    void testPatchModoSuccess() throws Exception {
        super.setupModerator();

        mockMvc.perform(patch("/support/ticket/message")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new PacifistaSupportTicketMessageDTO()))
        ).andExpect(status().isOk());
    }

    @Test
    void testGetAllAdminSuccess() throws Exception {
        super.setupPacifistaAdmin();

        mockMvc.perform(get("/support/ticket/message")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isOk());
    }

    @Test
    void testCreateAdminSuccess() throws Exception {
        super.setupPacifistaAdmin();

        final PacifistaSupportTicketMessageDTO ticketMessageDTO = new PacifistaSupportTicketMessageDTO();
        ticketMessageDTO.setTicket(new PacifistaSupportTicketDTO());
        ticketMessageDTO.setMessage("message" + UUID.randomUUID());
        ticketMessageDTO.setWrittenByName("yolo");
        ticketMessageDTO.setWrittenById(UUID.randomUUID().toString());

        mockMvc.perform(post("/support/ticket/message")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticketMessageDTO))
        ).andExpect(status().isOk());
    }

    @Test
    void testPatchAdminSuccess() throws Exception {
        super.setupPacifistaAdmin();

        mockMvc.perform(patch("/support/ticket/message")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new PacifistaSupportTicketMessageDTO()))
        ).andExpect(status().isOk());
    }

    @Test
    void testCreateTicketNoAuthWeb() throws Exception {
        mockMvc.perform(post("/support/ticket/message/web")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new PacifistaSupportTicketMessageDTO()))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void testCreateTicketMessageUserFromRouteWeb() throws Exception {
        final String clientIp = "8.2.0.1";
        super.setupNormal();

        final PacifistaSupportTicketMessageDTO ticketMessageDTO = new PacifistaSupportTicketMessageDTO();
        ticketMessageDTO.setTicket(new PacifistaSupportTicketDTO());
        ticketMessageDTO.setMessage("message" + UUID.randomUUID());
        final UUID token = UUID.randomUUID();

        mockMvc.perform(post("/support/ticket/message/web")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticketMessageDTO))
                .with(request -> {
                    request.setRemoteAddr(clientIp);
                    return request;
                })
        ).andExpect(status().isOk());

        mockMvc.perform(post("/support/ticket/message/web")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticketMessageDTO))
                .with(request -> {
                    request.setRemoteAddr(clientIp);
                    return request;
                })
        ).andExpect(status().isForbidden());
    }

    @Test
    void testCreateTicketMessageUserFromRouteWebWithNoMessage() throws Exception {
        super.setupNormal();

        final PacifistaSupportTicketMessageDTO ticketMessageDTO = new PacifistaSupportTicketMessageDTO();
        ticketMessageDTO.setTicket(new PacifistaSupportTicketDTO());

        mockMvc.perform(post("/support/ticket/message/web")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticketMessageDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testCreateTicketUserNoAuth() throws Exception {
        final PacifistaSupportTicketMessageDTO ticketMessageDTO = new PacifistaSupportTicketMessageDTO();
        ticketMessageDTO.setTicket(new PacifistaSupportTicketDTO());

        mockMvc.perform(post("/support/ticket/message/web")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticketMessageDTO))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void testGetUserTicketsNoAuth() throws Exception {
        mockMvc.perform(get("/support/ticket/message/web")).andExpect(status().isUnauthorized());
    }

    @Test
    void testGetUserTicketsMessageSuccess() throws Exception {
        final UserDTO userDTO = super.setupNormal();

        final PacifistaSupportTicketDTO ticketDTO = new PacifistaSupportTicketDTO();
        ticketDTO.setCreatedById(userDTO.getId().toString());

        when(this.pacifistaSupportTicketService.findById(anyString())).thenReturn(ticketDTO);

        mockMvc.perform(get("/support/ticket/message/web?ticketId=uuid")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isOk());
    }

    @Test
    void testGetUserTicketsWithNoTicketId() throws Exception {
        super.setupNormal();

        mockMvc.perform(get("/support/ticket/message/web")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

}
