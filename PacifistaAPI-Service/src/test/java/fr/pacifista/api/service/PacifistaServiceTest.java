package fr.pacifista.api.service;

import fr.funixgaming.api.client.user.clients.UserAuthClient;
import fr.funixgaming.api.client.user.dtos.UserDTO;
import fr.funixgaming.api.client.user.enums.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Getter
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
public abstract class PacifistaServiceTest {
    private final MockMvc mockMvc;
    private final JsonHelper jsonHelper;
    private final UserAuthClient authClient;

    public void setupAuth(final UserRole userRole) {
        final UserDTO userDTO = new UserDTO();
        userDTO.setCreatedAt(Date.from(Instant.now()));
        userDTO.setEmail("test@pacifista.fr");
        userDTO.setRole(userRole);
        userDTO.setUsername("testuser");
        userDTO.setId(UUID.randomUUID());

        when(authClient.current(anyString())).thenReturn(userDTO);
    }
}
