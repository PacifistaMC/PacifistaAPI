package fr.pacifista.api.service.boxes.resources;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import fr.pacifista.api.utils.JsonHelper;
import fr.pacifista.api.utils.ResourceTestHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

abstract class BoxModuleResourceTest<DTO extends ApiDTO> extends ResourceTestHandler {

    String route = "/box";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonHelper jsonHelper;

    @Test
    void testAccessAdmin() throws Exception {
        super.setupAdmin();

        mockMvc.perform(get(this.route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isOk());
    }

    @Test
    void testAccessModoRefused() throws Exception {
        super.setupModerator();

        mockMvc.perform(get(this.route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isForbidden());
        mockMvc.perform(get(this.route + '/')
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isForbidden());
    }

    @Test
    void testAccessUserRefused() throws Exception {
        super.setupNormal();

        mockMvc.perform(get(this.route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isForbidden());
        mockMvc.perform(get(this.route + '/')
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isForbidden());
    }

    @Test
    void testCreateAdmin() throws Exception {
        final DTO request = generateDTO();

        super.setupAdmin();

        mockMvc.perform(post(this.route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(request))
        ).andExpect(status().isOk());
    }

    @Test
    void testPatchAdmin() throws Exception {
        final DTO request = generateDTO();

        super.setupAdmin();

        mockMvc.perform(patch(this.route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(request))
        ).andExpect(status().isOk());
    }

    @Test
    void testDeleteAdmin() throws Exception {
        super.setupAdmin();

        mockMvc.perform(delete(this.route + "?id=" + UUID.randomUUID())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
        ).andExpect(status().isOk());
    }

    @Test
    void testAccessRefusedWithNoBearer() throws Exception {
        super.setupNormal();

        mockMvc.perform(get(this.route)).andExpect(status().isUnauthorized());
        mockMvc.perform(get(this.route + '/')).andExpect(status().isUnauthorized());
    }

    abstract DTO generateDTO();

}
