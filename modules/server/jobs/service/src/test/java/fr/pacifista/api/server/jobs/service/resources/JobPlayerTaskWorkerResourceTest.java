package fr.pacifista.api.server.jobs.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.enums.ServerGameMode;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerTaskWorkerClientImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskDTO;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskWorkerDTO;
import fr.pacifista.api.server.jobs.service.services.JobPlayerTaskService;
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
class JobPlayerTaskWorkerResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private JobPlayerTaskService jobPlayerTaskService;

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
        final JobPlayerTaskWorkerDTO jobPlayerTaskWorkerDTO = new JobPlayerTaskWorkerDTO();
        jobPlayerTaskWorkerDTO.setTask(generateTask());
        jobPlayerTaskWorkerDTO.setParticipationAmount(10);
        jobPlayerTaskWorkerDTO.setPlayerUuid(UUID.randomUUID().toString());
        jobPlayerTaskWorkerDTO.setGameMode(ServerGameMode.CREATIVE);
        jobPlayerTaskWorkerDTO.setJobName("jobName");

        final String route = "/" + JobPlayerTaskWorkerClientImpl.PATH;

        MvcResult result = mockMvc.perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token")
                        .content(jsonHelper.toJson(jobPlayerTaskWorkerDTO)))
                .andExpect(status().isOk())
                .andReturn();

        final JobPlayerTaskWorkerDTO res = jsonHelper.fromJson(result.getResponse().getContentAsString(), JobPlayerTaskWorkerDTO.class);
        res.setParticipationAmount(20);

        result = mockMvc.perform(patch(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token")
                        .content(jsonHelper.toJson(res)))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(res.getParticipationAmount(), jsonHelper.fromJson(result.getResponse().getContentAsString(), JobPlayerTaskWorkerDTO.class).getParticipationAmount());
    }

    private JobPlayerTaskDTO generateTask() {
        final JobPlayerTaskDTO jobPlayerTaskDTO = new JobPlayerTaskDTO();
        jobPlayerTaskDTO.setAmountRequired(10);
        jobPlayerTaskDTO.setAmountCollected(0);
        jobPlayerTaskDTO.setCollectedTaskItems(false);
        jobPlayerTaskDTO.setIsTaskCompleted(false);
        jobPlayerTaskDTO.setMaterialNameToCollect("materialNameToCollect");
        jobPlayerTaskDTO.setPlayerUuid(UUID.randomUUID().toString());
        jobPlayerTaskDTO.setGameMode(ServerGameMode.CREATIVE);
        jobPlayerTaskDTO.setJobName("jobName");

        return jobPlayerTaskService.create(jobPlayerTaskDTO);
    }

}
