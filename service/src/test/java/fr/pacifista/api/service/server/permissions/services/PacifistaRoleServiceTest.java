package fr.pacifista.api.service.permissions.services;

import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.permissions.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.client.permissions.dtos.PacifistaPlayerRoleDTO;
import fr.pacifista.api.client.permissions.dtos.PacifistaRoleDTO;
import fr.pacifista.api.service.server.permissions.services.PacifistaPermissionService;
import fr.pacifista.api.service.server.permissions.services.PacifistaPlayerRoleService;
import fr.pacifista.api.service.server.permissions.services.PacifistaRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PacifistaRoleServiceTest {

    @Autowired
    PacifistaRoleService roleService;

    @Autowired
    PacifistaPermissionService pacifistaPermissionService;

    @Autowired
    PacifistaPlayerRoleService pacifistaPlayerRoleService;

    @Test
    void testRoleCreation() {
        final PacifistaRoleDTO roleDTO = new PacifistaRoleDTO();
        roleDTO.setName(UUID.randomUUID().toString());
        roleDTO.setPlayerNameColor("smldk");
        roleDTO.setPrefix("prefix");
        roleDTO.setPower("a");
        roleDTO.setStaffRank(false);

        assertDoesNotThrow(() -> {
            final PacifistaRoleDTO createdRole = roleService.create(roleDTO);
            assertEquals(roleDTO.getName(), createdRole.getName());
            assertEquals(roleDTO.getPlayerNameColor(), createdRole.getPlayerNameColor());
            assertEquals(roleDTO.getPrefix(), createdRole.getPrefix());
            assertEquals(roleDTO.getPower(), createdRole.getPower());
            assertEquals(roleDTO.getStaffRank(), createdRole.getStaffRank());
        });
    }

    @Test
    void testRoleCreationAndCheckNameAlreadyExist() {
        final PacifistaRoleDTO roleDTO = new PacifistaRoleDTO();
        roleDTO.setName(UUID.randomUUID().toString());
        roleDTO.setPlayerNameColor("smldk");
        roleDTO.setPrefix("prefix");
        roleDTO.setPower("a");
        roleDTO.setStaffRank(false);

        assertDoesNotThrow(() -> {
            roleService.create(roleDTO);
            assertThrowsExactly(ApiBadRequestException.class, () ->
                    roleService.create(roleDTO));
        });
    }

    @Test
    void testRolePatch() {
        final PacifistaRoleDTO roleDTO = new PacifistaRoleDTO();
        roleDTO.setName("wesh wesh");
        roleDTO.setPlayerNameColor(UUID.randomUUID().toString());
        roleDTO.setPrefix(UUID.randomUUID().toString());
        roleDTO.setPower("a");
        roleDTO.setStaffRank(false);

        assertDoesNotThrow(() -> {
            final PacifistaRoleDTO createdRole = roleService.create(roleDTO);
            createdRole.setName("allo allo ?");

            final PacifistaRoleDTO patchedRole = roleService.update(createdRole);
            assertEquals(createdRole.getName(), patchedRole.getName());
            assertEquals(createdRole.getPlayerNameColor(), patchedRole.getPlayerNameColor());
            assertEquals(createdRole.getPrefix(), patchedRole.getPrefix());
            assertEquals(createdRole.getPower(), patchedRole.getPower());
            assertEquals(createdRole.getStaffRank(), patchedRole.getStaffRank());
        });
    }

    @Test
    void testDeleteRole() {
        final PacifistaRoleDTO roleDTO = new PacifistaRoleDTO();
        roleDTO.setName(UUID.randomUUID().toString());
        roleDTO.setPlayerNameColor("smldk");
        roleDTO.setPrefix("prefix");
        roleDTO.setPower("a");
        roleDTO.setStaffRank(false);

        assertDoesNotThrow(() -> {
            final PacifistaRoleDTO createdRole = roleService.create(roleDTO);
            roleService.delete(createdRole.getId().toString());

            assertThrowsExactly(ApiNotFoundException.class, () ->
                    roleService.findById(createdRole.getId().toString()));
        });
    }

    @Test
    void testDeleteRoleWithPermissions() {
        final PacifistaRoleDTO roleDTO = new PacifistaRoleDTO();
        roleDTO.setName(UUID.randomUUID().toString());
        roleDTO.setPlayerNameColor("smldk");
        roleDTO.setPrefix("prefix");
        roleDTO.setPower("a");
        roleDTO.setStaffRank(false);

        assertDoesNotThrow(() -> {
            final PacifistaRoleDTO createdRole = roleService.create(roleDTO);
            final PacifistaPermissionDTO permissionDTO = new PacifistaPermissionDTO();
            permissionDTO.setPermission("mlkdd");
            permissionDTO.setRoleId(createdRole.getId());

            final PacifistaPermissionDTO createdPermission = pacifistaPermissionService.create(permissionDTO);
            roleService.delete(createdRole.getId().toString());

            assertThrowsExactly(ApiNotFoundException.class, () -> {
                roleService.findById(createdRole.getId().toString());
                pacifistaPermissionService.findById(createdPermission.getId().toString());
            });
        });
    }

    @Test
    void testDeleteRoleWithPlayerRoles() {
        final PacifistaRoleDTO roleDTO = new PacifistaRoleDTO();
        roleDTO.setName(UUID.randomUUID().toString());
        roleDTO.setPlayerNameColor("smldk");
        roleDTO.setPrefix("prefix");
        roleDTO.setPower("a");
        roleDTO.setStaffRank(false);

        assertDoesNotThrow(() -> {
            final PacifistaRoleDTO createdRole = roleService.create(roleDTO);
            final PacifistaPlayerRoleDTO playerRoleDTO = new PacifistaPlayerRoleDTO();
            playerRoleDTO.setPlayerUuid(UUID.randomUUID());
            playerRoleDTO.setRole(createdRole);

            final PacifistaPlayerRoleDTO playerRoleCreated = pacifistaPlayerRoleService.create(playerRoleDTO);

            roleService.delete(createdRole.getId().toString());

            assertThrowsExactly(ApiNotFoundException.class, () -> {
                roleService.findById(createdRole.getId().toString());
                pacifistaPlayerRoleService.findById(playerRoleCreated.getId().toString());
            });
        });
    }

}
