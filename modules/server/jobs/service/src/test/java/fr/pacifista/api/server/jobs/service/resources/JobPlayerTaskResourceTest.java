package fr.pacifista.api.server.jobs.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.enums.ServerGameMode;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerTaskClientImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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
@RunWith(MockitoJUnitRunner.class)
class JobPlayerTaskResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockitoBean
    private UserAuthClient userAuthClient;

    @BeforeEach
    void setUp() {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.PACIFISTA_ADMIN);

        when(userAuthClient.current(anyString())).thenReturn(userDTO);
    }

    @Test
    void testCrud() throws Exception {
        final JobPlayerTaskDTO jobPlayerTaskDTO = new JobPlayerTaskDTO();
        jobPlayerTaskDTO.setAmountRequired(10);
        jobPlayerTaskDTO.setAmountCollected(0);
        jobPlayerTaskDTO.setCollectedTaskItems(0);
        jobPlayerTaskDTO.setIsTaskCompleted(false);
        jobPlayerTaskDTO.setMaterialNameToCollect("materialNameToCollect");
        jobPlayerTaskDTO.setPlayerUuid(UUID.randomUUID().toString());
        jobPlayerTaskDTO.setGameMode(ServerGameMode.CREATIVE);
        jobPlayerTaskDTO.setJobName("jobName");

        final String route = "/" + JobPlayerTaskClientImpl.PATH;

        MvcResult result = mockMvc.perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token")
                        .content(jsonHelper.toJson(jobPlayerTaskDTO)))
                .andExpect(status().isOk())
                .andReturn();

        final JobPlayerTaskDTO res = jsonHelper.fromJson(result.getResponse().getContentAsString(), JobPlayerTaskDTO.class);
        res.setAmountCollected(20);

        result = mockMvc.perform(patch(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token")
                        .content(jsonHelper.toJson(res)))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(res.getAmountCollected(), jsonHelper.fromJson(result.getResponse().getContentAsString(), JobPlayerTaskDTO.class).getAmountCollected());
    }

}
