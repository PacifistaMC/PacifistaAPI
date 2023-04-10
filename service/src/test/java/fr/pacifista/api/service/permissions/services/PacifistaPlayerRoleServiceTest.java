package fr.pacifista.api.service.permissions.services;

import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.client.permissions.dtos.PacifistaPlayerRoleDTO;
import fr.pacifista.api.client.permissions.dtos.PacifistaRoleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacifistaPlayerRoleServiceTest extends PacifistaRoleHandlerTest {

    @Autowired
    PacifistaPlayerRoleService playerRoleService;

    @Autowired
    PacifistaRoleService roleService;

    @Test
    void createNewPlayerRole() {
        final PacifistaRoleDTO roleDTO = super.generateRole();
        final PacifistaPlayerRoleDTO playerRoleDTO = new PacifistaPlayerRoleDTO();

        playerRoleDTO.setPlayerUuid(UUID.randomUUID());
        playerRoleDTO.setRole(roleDTO);

        assertDoesNotThrow(() -> {
            final PacifistaPlayerRoleDTO createdPlayerRole = playerRoleService.create(playerRoleDTO);
            assertEquals(playerRoleDTO.getPlayerUuid(), createdPlayerRole.getPlayerUuid());
            assertEquals(playerRoleDTO.getRole(), createdPlayerRole.getRole());
        });
    }

    @Test
    void patchRoleNoAccess() {
        final PacifistaRoleDTO roleDTO = super.generateRole();
        final PacifistaPlayerRoleDTO playerRoleDTO = new PacifistaPlayerRoleDTO();

        playerRoleDTO.setPlayerUuid(UUID.randomUUID());
        playerRoleDTO.setRole(roleDTO);

        assertDoesNotThrow(() -> {
            final PacifistaPlayerRoleDTO createdPlayerRole = playerRoleService.create(playerRoleDTO);

            assertThrowsExactly(ApiBadRequestException.class, () -> {
                playerRoleService.update(createdPlayerRole);
                playerRoleService.update(List.of(createdPlayerRole));
            });
        });
    }

}
