package fr.pacifista.api.service.permissions.services;

import fr.pacifista.api.client.permissions.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.client.permissions.dtos.PacifistaRoleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PacifistaPermissionsServiceTest extends PacifistaRoleHandlerTest {

    @Autowired
    PacifistaPermissionService pacifistaPermissionService;

    @Test
    void testPermissionCreation() {
        final PacifistaPermissionDTO permissionDTO = new PacifistaPermissionDTO();
        permissionDTO.setRoleId(super.generateRole().getId());
        permissionDTO.setPermission("permission");

        assertDoesNotThrow(() -> {
            final PacifistaPermissionDTO createdPermission = pacifistaPermissionService.create(permissionDTO);
            assertEquals(permissionDTO.getPermission(), createdPermission.getPermission());
            assertEquals(permissionDTO.getRoleId(), createdPermission.getRoleId());
        });
    }

    @Test
    void testPatchPermission() {
        final PacifistaRoleDTO roleDTO = super.generateRole();
        final PacifistaPermissionDTO permissionDTO = new PacifistaPermissionDTO();
        permissionDTO.setRoleId(roleDTO.getId());
        permissionDTO.setPermission("permission");

        assertDoesNotThrow(() -> {
            final PacifistaPermissionDTO createdPermission = pacifistaPermissionService.create(permissionDTO);
            createdPermission.setPermission("newPermission");

            final PacifistaPermissionDTO patchedPermission = pacifistaPermissionService.update(createdPermission);
            assertEquals(createdPermission.getPermission(), patchedPermission.getPermission());
        });
    }

}
