package fr.pacifista.api.service.web.news.service;

import fr.funixgaming.api.client.user.dtos.UserDTO;
import fr.pacifista.api.client.web.news.dtos.PacifistaNewsDTO;
import fr.pacifista.api.service.core.auth.entities.Session;
import fr.pacifista.api.service.core.auth.services.ActualSession;
import fr.pacifista.api.service.web.news.services.PacifistaNewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class PacifistaNewsServiceTest {

    @Autowired
    PacifistaNewsService pacifistaNewsService;

    @MockBean
    ActualSession actualSession;


    @BeforeEach
    void setupMock() {

    }

    @Test
    void createEntity() {
        final String username = "jacky";
        final UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        final Session session = new Session(userDTO, "10.0.0.1");
        reset(actualSession);
        when(actualSession.getActualSession()).thenReturn(session);

        final PacifistaNewsDTO pacifistaNewsDTO = new PacifistaNewsDTO();
        pacifistaNewsDTO.setTitle(UUID.randomUUID().toString());
        pacifistaNewsDTO.setSubtitle(UUID.randomUUID().toString());
        pacifistaNewsDTO.setArticleImageUrl(UUID.randomUUID().toString());
        pacifistaNewsDTO.setBody(UUID.randomUUID().toString());
        pacifistaNewsDTO.setName(UUID.randomUUID().toString());

        assertDoesNotThrow(() -> {
            final PacifistaNewsDTO createdPacifistaNewsDTO = pacifistaNewsService.create(pacifistaNewsDTO);
            assertEquals(username, createdPacifistaNewsDTO.getOriginalWriter());
            assertEquals(pacifistaNewsDTO.getTitle(), createdPacifistaNewsDTO.getTitle());
            assertEquals(pacifistaNewsDTO.getSubtitle(), createdPacifistaNewsDTO.getSubtitle());
            assertEquals(pacifistaNewsDTO.getArticleImageUrl(), createdPacifistaNewsDTO.getArticleImageUrl());
            assertEquals(pacifistaNewsDTO.getBody(), createdPacifistaNewsDTO.getBody());
            assertEquals(pacifistaNewsDTO.getName(), createdPacifistaNewsDTO.getName());
        });
    }

    @Test
    void patchEntity() {
        final String username = "jacky";
        final UserDTO userDTO = new UserDTO();

        userDTO.setUsername(username);
        final Session session = new Session(userDTO, "10.0.0.1");
        reset(actualSession);
        when(actualSession.getActualSession()).thenReturn(session);

        final PacifistaNewsDTO pacifistaNewsDTO = new PacifistaNewsDTO();
        pacifistaNewsDTO.setTitle(UUID.randomUUID().toString());
        pacifistaNewsDTO.setSubtitle(UUID.randomUUID().toString());
        pacifistaNewsDTO.setArticleImageUrl(UUID.randomUUID().toString());
        pacifistaNewsDTO.setBody(UUID.randomUUID().toString());
        pacifistaNewsDTO.setName(UUID.randomUUID().toString());

        assertDoesNotThrow(() -> {
            final PacifistaNewsDTO createdPacifistaNewsDTO = pacifistaNewsService.create(pacifistaNewsDTO);
            createdPacifistaNewsDTO.setTitle(UUID.randomUUID().toString());

            final String username2 = "jacky2";
            final UserDTO userDTO2 = new UserDTO();
            userDTO2.setUsername(username2);
            final Session session2 = new Session(userDTO2, "10.0.0.1");
            reset(actualSession);
            when(actualSession.getActualSession()).thenReturn(session2);

            final PacifistaNewsDTO patched = pacifistaNewsService.update(createdPacifistaNewsDTO);
            assertEquals(createdPacifistaNewsDTO.getTitle(), patched.getTitle());
            assertEquals(username, patched.getOriginalWriter());
            assertEquals(username2, patched.getUpdateWriter());
        });
    }

}
