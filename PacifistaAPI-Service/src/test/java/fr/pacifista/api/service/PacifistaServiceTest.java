package fr.pacifista.api.service;

import fr.funixgaming.api.client.user.clients.UserAuthClient;
import fr.funixgaming.api.client.user.dtos.UserDTO;
import fr.funixgaming.api.client.user.dtos.UserTokenDTO;
import fr.funixgaming.api.client.user.dtos.requests.UserCreationDTO;
import fr.funixgaming.api.client.user.dtos.requests.UserLoginDTO;
import fr.funixgaming.api.client.user.enums.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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

        final UserTokenDTO tokenDTO = new UserTokenDTO();
        tokenDTO.setUser(userDTO);
        tokenDTO.setToken("tokentest");
        tokenDTO.setCreatedAt(Date.from(Instant.now()));
        tokenDTO.setExpirationDate(Date.from(Instant.now().plusSeconds(100000)));
        tokenDTO.setId(UUID.randomUUID());

        when(authClient.current(anyString())).thenReturn(userDTO);
        when(authClient.login(any(UserLoginDTO.class), anyString())).thenReturn(tokenDTO);
        when(authClient.register(any(UserCreationDTO.class), anyString())).thenReturn(userDTO);
        doNothing().when(authClient).valid(anyString());
    }
}
