package fr.pacifista.api.server.box.service.resources;

import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.server.box.client.dtos.BoxDTO;
import fr.pacifista.api.server.box.client.dtos.PlayerBoxDTO;
import fr.pacifista.api.server.box.service.services.PlayerBoxService;
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
class PlayerBoxResourceTest extends BoxModuleResourceTest<PlayerBoxDTO> {

    @MockBean
    PlayerBoxService playerBoxService;

    @BeforeEach
    void setupMock() {
        super.route = "/box/player";

        reset(playerBoxService);
        when(playerBoxService.getAll(
                anyString(), anyString(), anyString(), anyString()
        )).thenReturn(new PageDTO<>());
        when(playerBoxService.create(any(PlayerBoxDTO.class))).thenReturn(new PlayerBoxDTO());
        when(playerBoxService.update(any(PlayerBoxDTO.class))).thenReturn(new PlayerBoxDTO());
        doNothing().when(playerBoxService).delete(anyString());
    }

    @Override
    PlayerBoxDTO generateDTO() {
        final PlayerBoxDTO playerBoxDTO = new PlayerBoxDTO();
        playerBoxDTO.setBox(new BoxDTO());
        playerBoxDTO.setPlayerUuid(UUID.randomUUID());
        playerBoxDTO.setAmount(10);

        return playerBoxDTO;
    }
}
