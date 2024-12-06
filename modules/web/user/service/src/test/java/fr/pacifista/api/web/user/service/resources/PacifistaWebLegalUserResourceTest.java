package fr.pacifista.api.web.user.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import com.google.gson.reflect.TypeToken;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalUserDTO;
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

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class PacifistaWebLegalUserResourceTest {

    private static final String ROUTE = "/web/user/legal-document-user";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockitoBean
    private UserAuthClient userAuthClient;

    @Test
    void testCrud() throws Exception {
        final UserDTO user = UserDTO.generateFakeDataForTestingPurposes();
        final PacifistaWebLegalUserDTO legalUserDTO = new PacifistaWebLegalUserDTO(
                UUID.randomUUID(),
                PacifistaLegalDocumentType.CGU
        );

        reset(userAuthClient);
        when(userAuthClient.current(anyString())).thenReturn(user);
        mockMvc.perform(post(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer ddavv")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(legalUserDTO)))
                .andExpect(status().isForbidden());

        mockMvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(legalUserDTO)))
                .andExpect(status().isUnauthorized());

        reset(userAuthClient);
        user.setRole(UserRole.ADMIN);
        when(userAuthClient.current(anyString())).thenReturn(user);

        MvcResult mvcResult = mockMvc.perform(post(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dda")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(legalUserDTO)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaWebLegalUserDTO createdLegalUserDTO = jsonHelper.fromJson(
                mvcResult.getResponse().getContentAsString(),
                PacifistaWebLegalUserDTO.class
        );
        assertEquals(legalUserDTO.getUserId(), createdLegalUserDTO.getUserId());
        assertEquals(legalUserDTO.getType(), createdLegalUserDTO.getType());

        mvcResult = mockMvc.perform(patch(ROUTE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dda")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(createdLegalUserDTO)))
                .andExpect(status().isOk()).andReturn();
        final PacifistaWebLegalUserDTO updatedLegalUserDTO = jsonHelper.fromJson(
                mvcResult.getResponse().getContentAsString(),
                PacifistaWebLegalUserDTO.class
        );
        assertNotEquals(createdLegalUserDTO.getUpdatedAt(), updatedLegalUserDTO.getUpdatedAt());

        mockMvc.perform(delete(ROUTE + "?id=" + createdLegalUserDTO.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dda")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(legalUserDTO)))
                .andExpect(status().isOk());
        mockMvc.perform(delete(ROUTE + "?id=" + createdLegalUserDTO.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dda")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(legalUserDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUserSelfUpdate() throws Exception {
        final UserDTO user = UserDTO.generateFakeDataForTestingPurposes();
        user.setRole(UserRole.USER);
        when(userAuthClient.current(anyString())).thenReturn(user);

        this.mockMvc.perform(get(ROUTE + "/self"))
                .andExpect(status().isUnauthorized());
        MvcResult mvcResult = mockMvc.perform(get(ROUTE + "/self")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd"))
                .andExpect(status().isOk()).andReturn();
        final Collection<PacifistaWebLegalUserDTO> legalUserDTOS = getResponse(mvcResult);
        assertTrue(legalUserDTOS.isEmpty());

        final PacifistaLegalDocumentType legalDocumentType = PacifistaLegalDocumentType.CGU;
        mvcResult = mockMvc.perform(post(ROUTE + "/self")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(List.of(legalDocumentType))))
                .andExpect(status().isOk()).andReturn();
        final Collection<PacifistaWebLegalUserDTO> updatedLegalUserDTOS = getResponse(mvcResult);
        assertFalse(updatedLegalUserDTOS.isEmpty());
        assertEquals(1, updatedLegalUserDTOS.size());
        final PacifistaWebLegalUserDTO updatedLegalUserDTO = updatedLegalUserDTOS.iterator().next();
        assertEquals(legalDocumentType, updatedLegalUserDTO.getType());
        assertNull(updatedLegalUserDTO.getUpdatedAt());

       mvcResult = mockMvc.perform(get(ROUTE + "/self")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd"))
                .andExpect(status().isOk()).andReturn();
        final Collection<PacifistaWebLegalUserDTO> legalUserDTOSTestAfterCreate = getResponse(mvcResult);
        assertFalse(legalUserDTOSTestAfterCreate.isEmpty());
        assertEquals(1, legalUserDTOSTestAfterCreate.size());

        mvcResult = mockMvc.perform(post(ROUTE + "/self")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(List.of(legalDocumentType))))
                .andExpect(status().isOk()).andReturn();
        final Collection<PacifistaWebLegalUserDTO> updatedLegalUserDTOS2 = getResponse(mvcResult);
        assertFalse(updatedLegalUserDTOS2.isEmpty());
        assertEquals(1, updatedLegalUserDTOS2.size());
        final PacifistaWebLegalUserDTO updatedLegalUserDTO2 = updatedLegalUserDTOS2.iterator().next();
        assertEquals(legalDocumentType, updatedLegalUserDTO2.getType());
        assertNotNull(updatedLegalUserDTO2.getUpdatedAt());

        final PacifistaLegalDocumentType legalDocumentType2 = PacifistaLegalDocumentType.CGV;
        mvcResult = mockMvc.perform(post(ROUTE + "/self")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(List.of(legalDocumentType, legalDocumentType2))))
                .andExpect(status().isOk()).andReturn();
        final Collection<PacifistaWebLegalUserDTO> updatedLegalUserDTOS3 = getResponse(mvcResult);
        assertFalse(updatedLegalUserDTOS3.isEmpty());
        assertEquals(2, updatedLegalUserDTOS3.size());

        mvcResult = mockMvc.perform(get(ROUTE + "/self")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd"))
                .andExpect(status().isOk()).andReturn();
        final Collection<PacifistaWebLegalUserDTO> legalUserDTOSTestAfterCreate2 = getResponse(mvcResult);
        assertFalse(legalUserDTOSTestAfterCreate2.isEmpty());
        assertEquals(2, legalUserDTOSTestAfterCreate2.size());
    }

    private Collection<PacifistaWebLegalUserDTO> getResponse(final MvcResult result) throws Exception {
        final Type type = new TypeToken<Collection<PacifistaWebLegalUserDTO>>() {}.getType();
        return jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
    }

}
