package fr.pacifista.api.server.box.service.resources;

import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.core.client.enums.enums.ServerGameMode;
import fr.pacifista.api.server.box.client.dtos.BoxDTO;
import fr.pacifista.api.server.box.service.services.BoxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class BoxResourceTest extends BoxModuleResourceTest<BoxDTO> {

    @MockBean
    BoxService boxService;

    @BeforeEach
    void setupMock() {
        super.route = "/box";

        reset(boxService);
        when(boxService.getAll(
                anyString(), anyString(), anyString(), anyString()
        )).thenReturn(new PageDTO<>());
        when(boxService.create(any(BoxDTO.class))).thenReturn(new BoxDTO());
        when(boxService.update(any(BoxDTO.class))).thenReturn(new BoxDTO());
        doNothing().when(boxService).delete(anyString());
    }

    @Override
    BoxDTO generateDTO() {
        final BoxDTO request = new BoxDTO();
        request.setBoxName("name" + UUID.randomUUID());
        request.setBoxDescription("desc" + UUID.randomUUID());
        request.setBoxDisplayName("dpname" + UUID.randomUUID());
        request.setDropAmount(10);
        request.setGameMode(ServerGameMode.SKYBLOCK);

        return request;
    }
}
