package fr.pacifista.api.server.jobs.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.core.client.enums.ServerGameMode;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerQuestClientImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerQuestDTO;
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
class JobPlayerQuestResourceTest {

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
        JobPlayerQuestDTO jobPlayerQuestDTO = new JobPlayerQuestDTO();

        jobPlayerQuestDTO.setQuestName("questName");
        jobPlayerQuestDTO.setFinished(false);
        jobPlayerQuestDTO.setJsonQuestData("{}");
        jobPlayerQuestDTO.setPlayerUuid(UUID.randomUUID().toString());
        jobPlayerQuestDTO.setGameMode(ServerGameMode.CREATIVE);
        jobPlayerQuestDTO.setJobName("jobName");

        final String route = "/" + JobPlayerQuestClientImpl.PATH;

        MvcResult result = mockMvc.perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token")
                        .content(jsonHelper.toJson(jobPlayerQuestDTO)))
                .andExpect(status().isOk())
                .andReturn();

        JobPlayerQuestDTO jobPlayerQuestDTOResult = jsonHelper.fromJson(result.getResponse().getContentAsString(), JobPlayerQuestDTO.class);
        jobPlayerQuestDTOResult.setQuestName("questName2");

        result = mockMvc.perform(patch(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer token")
                        .content(jsonHelper.toJson(jobPlayerQuestDTOResult)))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(jobPlayerQuestDTOResult.getQuestName(), jsonHelper.fromJson(result.getResponse().getContentAsString(), JobPlayerQuestDTO.class).getQuestName());
    }

}
