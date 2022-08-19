package fr.pacifista.api.service.permissions;

import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaPlayerRoleDTO;
import fr.pacifista.api.client.modules.permissions.dtos.PacifistaRoleDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import fr.pacifista.api.service.permissions.services.PacifistaPlayerRoleService;
import fr.pacifista.api.service.utils.PacifistaServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PacifistaPlayerRoleTests extends PacifistaServiceTest {

    @Autowired
    private PacifistaPermissionComponentTest permissionComponentTest;

    @Autowired
    private PacifistaPlayerRoleService playerRoleService;

    @Test
    public void createNewPlayerRoleSuccess() {
        final PacifistaRole role = permissionComponentTest.createNewRole(false);

        final PacifistaPlayerRoleDTO request = new PacifistaPlayerRoleDTO();
        request.setPlayerUuid(UUID.randomUUID());
        request.setRole(permissionComponentTest.getRoleService().getMapper().toDto(role));

        final PacifistaPlayerRoleDTO response = playerRoleService.create(request);
        assertEquals(request.getPlayerUuid(), response.getPlayerUuid());
        assertEquals(request.getRole(), response.getRole());

        final PacifistaPlayerRoleDTO request2 = new PacifistaPlayerRoleDTO();
        request2.setPlayerUuid(UUID.randomUUID());
        request2.setRole(permissionComponentTest.getRoleService().getMapper().toDto(role));

        final PacifistaPlayerRoleDTO response2 = playerRoleService.create(request2);
        assertEquals(request2.getPlayerUuid(), response2.getPlayerUuid());
        assertEquals(request2.getRole(), response2.getRole());
    }

    @Test
    public void createNewPlayerRoleFailNoRoleId() {
        try {
            final PacifistaPlayerRoleDTO request = new PacifistaPlayerRoleDTO();
            request.setPlayerUuid(UUID.randomUUID());

            playerRoleService.create(request);
            fail();
        } catch (ApiBadRequestException ignored) {}
    }

    @Test
    public void createNewPlayerRoleFailRoleIdNotFound() {
        try {
            final PacifistaPlayerRoleDTO request = new PacifistaPlayerRoleDTO();
            final PacifistaRoleDTO roleDTO = new PacifistaRoleDTO();

            roleDTO.setId(UUID.randomUUID());
            request.setPlayerUuid(UUID.randomUUID());
            request.setRole(roleDTO);

            playerRoleService.create(request);
            fail();
        } catch (ApiNotFoundException ignored) {}
    }

    @Test
    public void updatePlayerRoleCheckFailSuccess() {
        try {
            this.playerRoleService.update(new PacifistaPlayerRoleDTO());
            this.playerRoleService.update(List.of(new PacifistaPlayerRoleDTO()));
            fail();
        } catch (ApiBadRequestException ignored) {
        }
    }

}
