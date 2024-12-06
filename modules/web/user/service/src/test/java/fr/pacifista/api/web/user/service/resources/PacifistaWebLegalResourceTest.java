package fr.pacifista.api.web.user.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalDTO;
import fr.pacifista.api.web.user.client.enums.PacifistaLegalDocumentType;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class PacifistaWebLegalResourceTest {

    private static final String ROUTE = "/web/user/legal-document";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockitoBean
    private UserAuthClient userAuthClient;

    @Test
    void testCrud() throws Exception {
        final UserDTO user = UserDTO.generateFakeDataForTestingPurposes();
        final PacifistaWebLegalDTO legalDTO = new PacifistaWebLegalDTO(
                "<h1>Legal document</h1>",
                PacifistaLegalDocumentType.CGU,
                null
        );

        when(userAuthClient.current(anyString())).thenReturn(user);

        mockMvc.perform(post(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(legalDTO)))
                .andExpect(status().isForbidden());

        mockMvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(legalDTO)))
                .andExpect(status().isUnauthorized());

        user.setRole(UserRole.ADMIN);
        when(userAuthClient.current(anyString())).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(post(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dda")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(legalDTO)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaWebLegalDTO createdLegalDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PacifistaWebLegalDTO.class);
        assertNull(createdLegalDTO.getUpdatedAt());
        assertNull(createdLegalDTO.getEditReason());
        assertEquals(legalDTO.getContentHtml(), createdLegalDTO.getContentHtml());
        assertEquals(legalDTO.getType(), createdLegalDTO.getType());

        createdLegalDTO.setContentHtml("<h1>Legal document updated</h1>");
        createdLegalDTO.setEditReason("Update legal document for unit test");
        mvcResult = mockMvc.perform(patch(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dda")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(createdLegalDTO)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaWebLegalDTO updatedLegalDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), PacifistaWebLegalDTO.class);
        assertEquals(createdLegalDTO.getContentHtml(), updatedLegalDTO.getContentHtml());
        assertEquals(createdLegalDTO.getType(), updatedLegalDTO.getType());
        assertEquals(createdLegalDTO.getEditReason(), updatedLegalDTO.getEditReason());
        assertNotNull(updatedLegalDTO.getUpdatedAt());
        assertNotNull(updatedLegalDTO.getEditReason());

        mockMvc.perform(delete(ROUTE + "?id=" + updatedLegalDTO.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer dda"))
                .andExpect(status().isOk());
        mockMvc.perform(delete(ROUTE + "?id=" + updatedLegalDTO.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dda"))
                .andExpect(status().isNotFound());
    }

}
