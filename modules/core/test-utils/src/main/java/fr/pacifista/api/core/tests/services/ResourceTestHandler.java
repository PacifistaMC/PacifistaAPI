package fr.pacifista.api.core.tests.services;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * SpringBootTest
 * RunWith(MockitoJUnitRunner.class)
 */
public abstract class ResourceTestHandler {

    @MockBean
    UserAuthClient authClient;

    public UserDTO setupPacifistaAdmin() {
        reset(authClient);

        final UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test.admin.mock@gmail.com");
        userDTO.setUsername("testuser-admin");
        userDTO.setRole(UserRole.PACIFISTA_ADMIN);
        userDTO.setId(UUID.randomUUID());
        userDTO.setCreatedAt(new Date());

        when(authClient.current(anyString())).thenReturn(userDTO);
        return userDTO;
    }

    public UserDTO setupAdmin() {
        reset(authClient);

        final UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test.admin.mock@gmail.com");
        userDTO.setUsername("testuser-admin");
        userDTO.setRole(UserRole.ADMIN);
        userDTO.setId(UUID.randomUUID());
        userDTO.setCreatedAt(new Date());

        when(authClient.current(anyString())).thenReturn(userDTO);
        return userDTO;
    }

    public UserDTO setupModerator() {
        reset(authClient);

        final UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test.modo.mock@gmail.com");
        userDTO.setUsername("testuser-modo");
        userDTO.setRole(UserRole.PACIFISTA_MODERATOR);
        userDTO.setId(UUID.randomUUID());
        userDTO.setCreatedAt(new Date());

        when(authClient.current(anyString())).thenReturn(userDTO);
        return userDTO;
    }

    public UserDTO setupNormal() {
        reset(authClient);

        final UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test.user.mock@gmail.com");
        userDTO.setUsername("testuser-user");
        userDTO.setRole(UserRole.USER);
        userDTO.setId(UUID.randomUUID());
        userDTO.setCreatedAt(new Date());

        when(authClient.current(anyString())).thenReturn(userDTO);
        return userDTO;
    }

    public void resetClient() {
        reset(authClient);
    }

}
