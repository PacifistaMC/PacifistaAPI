package fr.pacifista.api.core.client.mojang.clients;

import fr.pacifista.api.core.client.mojang.dto.MojangUserNameAndIdDTO;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MojangApiImplClientTest {

    private static final String USERNAME = "FunixGaming";
    private static final UUID UUID_PLAYER = UUID.fromString("24732545-e33a-40b2-8db6-4129ba9bd49e");

    private final MojangApiImplClient mojangApiImplClient = new MojangApiImplClient();

    void testGetIdByUsernameOne() {
        assertDoesNotThrow(() -> {
            final MojangUserNameAndIdDTO userNameAndIdDTO = this.mojangApiImplClient.getUserIdByUsername(USERNAME);

            assertNotNull(userNameAndIdDTO);
            assertEquals(USERNAME, userNameAndIdDTO.getName());
            assertEquals(UUID_PLAYER, userNameAndIdDTO.getId());
        });
    }

    void testGetIdByUsernameOneNotExists() {
        assertDoesNotThrow(() -> {
            final MojangUserNameAndIdDTO userNameAndIdDTO = this.mojangApiImplClient.getUserIdByUsername("lkjqshdfpkhzeapoffqsd");
            assertNull(userNameAndIdDTO);
        });
    }

    void testGetIdByUsernameMultiple() {
        assertDoesNotThrow(() -> {
            final List<MojangUserNameAndIdDTO> userNameAndIdDTO = this.mojangApiImplClient.getUserIdByUsername(List.of(USERNAME, "lkjqshdfpkhzeapoffqsd"));

            assertEquals(1, userNameAndIdDTO.size());
            assertEquals(USERNAME, userNameAndIdDTO.get(0).getName());
            assertEquals(UUID_PLAYER, userNameAndIdDTO.get(0).getId());
        });
    }

    void testGetUsernameById() {
        assertDoesNotThrow(() -> {
            final MojangUserNameAndIdDTO userNameAndIdDTO = this.mojangApiImplClient.getUsernameByUuid(UUID_PLAYER.toString());

            assertNotNull(userNameAndIdDTO);
            assertEquals(UUID_PLAYER, userNameAndIdDTO.getId());
            assertEquals(USERNAME, userNameAndIdDTO.getName());
        });
    }

}
