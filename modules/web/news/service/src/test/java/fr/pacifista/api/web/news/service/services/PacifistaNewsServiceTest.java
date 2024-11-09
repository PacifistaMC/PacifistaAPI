package fr.pacifista.api.web.news.service.services;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.dtos.UserSession;
import com.funixproductions.api.user.client.security.CurrentSession;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;

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
    CurrentSession actualSession;

    @Test
    void createEntity() {
        final String username = "jacky";
        final UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        final UserSession session = new UserSession(userDTO, "10.0.0.1", new MockHttpServletRequest());
        reset(actualSession);
        when(actualSession.getUserSession()).thenReturn(session);

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
        final UserSession session = new UserSession(userDTO, "10.0.0.1", new MockHttpServletRequest());
        reset(actualSession);
        when(actualSession.getUserSession()).thenReturn(session);

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
            final UserSession session2 = new UserSession(userDTO2, "10.0.0.1", new MockHttpServletRequest());
            reset(actualSession);
            when(actualSession.getUserSession()).thenReturn(session2);

            final PacifistaNewsDTO patched = pacifistaNewsService.update(createdPacifistaNewsDTO);
            assertEquals(createdPacifistaNewsDTO.getTitle(), patched.getTitle());
            assertEquals(username, patched.getOriginalWriter());
            assertEquals(username2, patched.getUpdateWriter());
        });
    }

}
