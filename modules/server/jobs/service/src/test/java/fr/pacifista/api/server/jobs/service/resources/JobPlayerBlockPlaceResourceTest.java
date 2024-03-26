package fr.pacifista.api.server.jobs.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerBlockPlaceClientImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerBlockPlaceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JobPlayerBlockPlaceResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockBean
    private UserAuthClient userAuthClient;

    @BeforeEach
    void setUp() {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.PACIFISTA_ADMIN);

        when(userAuthClient.current(anyString())).thenReturn(userDTO);
    }

    @Test
    void testCrud() throws Exception {
        final JobPlayerBlockPlaceDTO jobPlayerBlockPlaceDTO = new JobPlayerBlockPlaceDTO();
        jobPlayerBlockPlaceDTO.setBlockType("blockName");
        jobPlayerBlockPlaceDTO.setWorldUuid(UUID.randomUUID());
        jobPlayerBlockPlaceDTO.setServerType(ServerType.CREATIVE);
        jobPlayerBlockPlaceDTO.setX(1.0);
        jobPlayerBlockPlaceDTO.setY(2.0);
        jobPlayerBlockPlaceDTO.setZ(3.0);
        jobPlayerBlockPlaceDTO.setYaw(4.0f);
        jobPlayerBlockPlaceDTO.setPitch(5.0f);

        MvcResult mvcResult = mockMvc.perform(post("/" + JobPlayerBlockPlaceClientImpl.PATH)
                .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(jsonHelper.toJson(jobPlayerBlockPlaceDTO)))
                .andExpect(status().isOk()).andReturn();

        final JobPlayerBlockPlaceDTO jobPlayerBlockPlaceDTOResponse = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), JobPlayerBlockPlaceDTO.class);
        assertEquals(jobPlayerBlockPlaceDTO.getBlockType(), jobPlayerBlockPlaceDTOResponse.getBlockType());

        jobPlayerBlockPlaceDTOResponse.setBlockType("sdlkfsdkjfsmdlkfj");
        mvcResult = mockMvc.perform(patch("/" + JobPlayerBlockPlaceClientImpl.PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(jobPlayerBlockPlaceDTOResponse)))
                .andExpect(status().isOk()).andReturn();

        final JobPlayerBlockPlaceDTO jobPlayerBlockPlaceDTOResponse2 = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), JobPlayerBlockPlaceDTO.class);
        assertEquals(jobPlayerBlockPlaceDTOResponse.getBlockType(), jobPlayerBlockPlaceDTOResponse2.getBlockType());
    }

}
