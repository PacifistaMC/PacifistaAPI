package fr.pacifista.api.service.permissions;

import fr.pacifista.api.service.permissions.entities.PacifistaPermission;
import fr.pacifista.api.service.permissions.entities.PacifistaPlayerRole;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import fr.pacifista.api.service.permissions.entities.PacifistaRoleHeritage;
import fr.pacifista.api.service.permissions.repositories.PacifistaRoleHeritageRepository;
import fr.pacifista.api.service.permissions.services.PacifistaPermissionService;
import fr.pacifista.api.service.permissions.services.PacifistaPlayerRoleService;
import fr.pacifista.api.service.permissions.services.PacifistaRoleService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Component
public class PacifistaPermissionComponentTest {

    @Autowired
    private PacifistaRoleService roleService;

    @Autowired
    private PacifistaPermissionService permissionService;

    @Autowired
    private PacifistaPlayerRoleService playerRoleService;

    @Autowired
    private PacifistaRoleHeritageRepository roleHeritageRepository;

    public PacifistaRole createNewRole(boolean staff) {
        final PacifistaRole role = new PacifistaRole();

        role.setName("role " + UUID.randomUUID());
        role.setPower("a");
        role.setPrefix("[test]");
        role.setPlayerNameColor("BLUE");
        role.setStaffRank(staff);
        return roleService.getRepository().save(role);
    }

    public PacifistaPermission createPermission(final PacifistaRole role) {
        final PacifistaPermission pacifistaPermission = new PacifistaPermission();

        pacifistaPermission.setPermission("test.perm");
        pacifistaPermission.setRole(role);
        return permissionService.getRepository().save(pacifistaPermission);
    }

    public PacifistaPlayerRole createNewPlayerRole(final PacifistaRole role) {
        final PacifistaPlayerRole playerRole = new PacifistaPlayerRole();

        playerRole.setPlayerUuid(UUID.randomUUID());
        playerRole.setRole(role);
        return playerRoleService.getRepository().save(playerRole);
    }

    public PacifistaRoleHeritage createNewHeritage(final PacifistaRole role, final PacifistaRole heritage) {
        final PacifistaRoleHeritage heritageEnt = new PacifistaRoleHeritage();

        heritageEnt.setRole(role);
        heritageEnt.setHeritage(heritage);
        return roleHeritageRepository.save(heritageEnt);
    }

}
