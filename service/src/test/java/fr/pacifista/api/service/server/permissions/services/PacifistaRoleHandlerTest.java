package fr.pacifista.api.service.server.permissions.services;

import fr.pacifista.api.client.server.permissions.dtos.PacifistaRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

abstract class PacifistaRoleHandlerTest {

    @Autowired
    PacifistaRoleService roleService;

    PacifistaRoleDTO generateRole() {
        final PacifistaRoleDTO roleDTO = new PacifistaRoleDTO();
        roleDTO.setName(UUID.randomUUID().toString());
        roleDTO.setPlayerNameColor(UUID.randomUUID().toString());
        roleDTO.setPrefix(UUID.randomUUID().toString());
        roleDTO.setPower(UUID.randomUUID().toString());
        roleDTO.setStaffRank(false);

        return roleService.create(roleDTO);
    }

}
