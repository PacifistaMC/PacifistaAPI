package fr.pacifista.api.support.tickets.service.resources;

import com.funixproductions.api.google.recaptcha.client.services.GoogleRecaptchaHandler;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import com.google.gson.reflect.TypeToken;
import fr.pacifista.api.core.tests.services.ResourceTestHandler;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketDTO;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class PacifistaSupportTicketResourceIntegrationTest extends ResourceTestHandler {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonHelper jsonHelper;

    @Autowired
    PacifistaSupportTicketService ticketService;

    @MockBean
    GoogleRecaptchaHandler captchaClient;

    @BeforeEach
    void setupCaptcha() {
        reset(this.captchaClient);
        doNothing().when(this.captchaClient).verify(any(), any());
    }

    @Test
    void fetchUserTickets() throws Exception {
        final Type type = new TypeToken<PageDTO<PacifistaSupportTicketDTO>>(){}.getType();

        UserDTO userDTO = super.setupNormal();

        PacifistaSupportTicketDTO ticket1 = new PacifistaSupportTicketDTO();
        ticket1.setObject("Test");
        ticket1.setType(TicketType.BUG);

        MvcResult result = this.mockMvc.perform(post("/support/ticket/web")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(ticket1))
                ).andExpect(status().isOk()).andReturn();
        ticket1 = this.jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaSupportTicketDTO.class);

        result = this.mockMvc.perform(get("/support/ticket/web")
                .header("Authorization", "Bearer token")
        ).andExpect(status().isOk()).andReturn();

        PageDTO<PacifistaSupportTicketDTO> page = this.jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
        assertEquals(1, page.getContent().size());
        assertTrue(isTicketPresentInList(page.getContent(), ticket1));
        assertEquals(userDTO.getId().toString(), page.getContent().get(0).getCreatedById());

        userDTO = super.setupModerator();

        ticket1.setId(null);
        result = this.mockMvc.perform(post("/support/ticket/web")
                .header("Authorization", "Bearer tokenModo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticket1))
                .with(request -> {
                    request.setRemoteAddr("1.0.3.4");
                    return request;
                })
        ).andExpect(status().isOk()).andReturn();
        ticket1 = this.jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaSupportTicketDTO.class);

        result = this.mockMvc.perform(get("/support/ticket/web")
                .header("Authorization", "Bearer tokenModo")
        ).andExpect(status().isOk()).andReturn();

        page = this.jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
        assertEquals(1, page.getContent().size());
        assertTrue(isTicketPresentInList(page.getContent(), ticket1));
        assertEquals(userDTO.getId().toString(), page.getContent().get(0).getCreatedById());
    }

    @Test
    void testFetchUserDoneTickets() throws Exception {
        final Type type = new TypeToken<PageDTO<PacifistaSupportTicketDTO>>(){}.getType();

        UserDTO userDTO = super.setupNormal();

        PacifistaSupportTicketDTO ticket1 = new PacifistaSupportTicketDTO();
        ticket1.setObject("Test");
        ticket1.setType(TicketType.BUG);

        MvcResult result = this.mockMvc.perform(post("/support/ticket/web")
                .header("Authorization", "Bearer token2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(ticket1))
                .with(request -> {
                    request.setRemoteAddr("1.0.3.7");
                    return request;
                })
        ).andExpect(status().isOk()).andReturn();
        ticket1 = this.jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaSupportTicketDTO.class);

        result = this.mockMvc.perform(get("/support/ticket/web")
                .header("Authorization", "Bearer token2")
        ).andExpect(status().isOk()).andReturn();

        PageDTO<PacifistaSupportTicketDTO> page = this.jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
        assertEquals(1, page.getContent().size());
        assertTrue(isTicketPresentInList(page.getContent(), ticket1));
        assertEquals(userDTO.getId().toString(), page.getContent().get(0).getCreatedById());

        ticket1.setStatus(TicketStatus.SOLVED);
        ticket1 = this.ticketService.update(ticket1);

        result = this.mockMvc.perform(get("/support/ticket/web")
                .header("Authorization", "Bearer token2")
        ).andExpect(status().isOk()).andReturn();

        page = this.jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
        assertEquals(1, page.getContent().size());
        assertTrue(isTicketPresentInList(page.getContent(), ticket1));
        assertEquals(userDTO.getId().toString(), page.getContent().get(0).getCreatedById());

        result = this.mockMvc.perform(get("/support/ticket/web?ticketStatus=" + TicketStatus.IN_PROGRESS)
                .header("Authorization", "Bearer token2")
        ).andExpect(status().isOk()).andReturn();

        page = this.jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
        assertEquals(0, page.getContent().size());

        result = this.mockMvc.perform(get("/support/ticket/web?ticketStatus=[" + TicketStatus.IN_PROGRESS + "|" + TicketStatus.CREATED + "]")
                .header("Authorization", "Bearer token2")
        ).andExpect(status().isOk()).andReturn();

        page = this.jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
        assertEquals(0, page.getContent().size());
    }

    private boolean isTicketPresentInList(final List<PacifistaSupportTicketDTO> list, final PacifistaSupportTicketDTO ticket) {
        for (final PacifistaSupportTicketDTO elem : list) {
            if (elem.equals(ticket)) {
                return true;
            }
        }
        return false;
    }

}
