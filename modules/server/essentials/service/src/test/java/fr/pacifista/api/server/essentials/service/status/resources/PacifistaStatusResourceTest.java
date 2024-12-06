package fr.pacifista.api.server.essentials.service.status.resources;

import fr.pacifista.api.server.essentials.client.status.clients.PacifistaStatusImplClient;
import fr.pacifista.api.server.essentials.client.status.dtos.PacifistaServerInfoDTO;
import fr.pacifista.api.server.essentials.service.status.clients.PacifistaInternalStatusClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PacifistaStatusResourceTest {

    private final String route = "/" + PacifistaStatusImplClient.PATH;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PacifistaStatusResource resource;

    @MockitoBean
    private PacifistaInternalStatusClient pacifistaInternalStatusClient;

    @BeforeEach
    void setupMock() {
        when(pacifistaInternalStatusClient.getServerInfo()).thenReturn(new PacifistaServerInfoDTO());
        resource.updateInfos();
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(route))
                .andExpect(status().isOk());
    }
}
