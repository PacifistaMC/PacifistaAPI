package fr.pacifista.api.server.box.service.resources;

import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.server.box.client.dtos.BoxRewardDTO;
import fr.pacifista.api.server.box.service.services.BoxRewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class BoxRewardResourceTest extends BoxModuleResourceTest<BoxRewardDTO> {

    @MockitoBean
    BoxRewardService boxRewardService;

    @BeforeEach
    void setupMock() {
        super.route = "/box/rewards";

        reset(boxRewardService);
        when(boxRewardService.getAll(
                anyString(), anyString(), anyString(), anyString()
        )).thenReturn(new PageDTO<>());
        when(boxRewardService.create(any(BoxRewardDTO.class))).thenReturn(new BoxRewardDTO());
        when(boxRewardService.update(any(BoxRewardDTO.class))).thenReturn(new BoxRewardDTO());
        doNothing().when(boxRewardService).delete(anyString());
    }

    @Override
    BoxRewardDTO generateDTO() {
        final BoxRewardDTO rewardDTO = new BoxRewardDTO();

        rewardDTO.setBoxId(UUID.randomUUID());
        rewardDTO.setItemSerialized(UUID.randomUUID().toString());
        rewardDTO.setRarity(10.0f);
        return rewardDTO;
    }
}
