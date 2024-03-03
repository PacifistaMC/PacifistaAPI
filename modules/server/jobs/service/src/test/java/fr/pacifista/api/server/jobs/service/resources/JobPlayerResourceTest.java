package fr.pacifista.api.server.jobs.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.enums.ServerGameMode;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerClientImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JobPlayerResourceTest {

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
        final JobPlayerDTO jobPlayerDTO = new JobPlayerDTO();
        jobPlayerDTO.setExp(0.0);
        jobPlayerDTO.setLevel(1);
        jobPlayerDTO.setPoints(10);
        jobPlayerDTO.setIsActive(true);
        jobPlayerDTO.setPlayerUuid(UUID.randomUUID().toString());
        jobPlayerDTO.setGameMode(ServerGameMode.CREATIVE);
        jobPlayerDTO.setJobName("jobName");

        final String route = "/" + JobPlayerClientImpl.PATH;

        MvcResult result = mockMvc.perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token")
                        .content(jsonHelper.toJson(jobPlayerDTO)))
                .andExpect(status().isOk())
                .andReturn();

        final JobPlayerDTO res = jsonHelper.fromJson(result.getResponse().getContentAsString(), JobPlayerDTO.class);
        res.setPoints(20);

        result = mockMvc.perform(patch(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token")
                        .content(jsonHelper.toJson(res)))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(res.getPoints(), jsonHelper.fromJson(result.getResponse().getContentAsString(), JobPlayerDTO.class).getPoints());
    }

}
