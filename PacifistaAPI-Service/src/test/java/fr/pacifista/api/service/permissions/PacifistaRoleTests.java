package fr.pacifista.api.service.permissions;

import fr.pacifista.api.service.permissions.entities.PacifistaPermission;
import fr.pacifista.api.service.permissions.entities.PacifistaPlayerRole;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import fr.pacifista.api.service.permissions.repositories.PacifistaPermissionRepository;
import fr.pacifista.api.service.permissions.repositories.PacifistaPlayerRoleRepository;
import fr.pacifista.api.service.utils.PacifistaServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PacifistaRoleTests extends PacifistaServiceTest {

    private static final String ROUTE = "/gameroles";

    @Autowired
    private PacifistaPermissionComponentTest permissionComponentTest;

    @Autowired
    private PacifistaPlayerRoleRepository playerRoleRepository;

    @Autowired
    private PacifistaPermissionRepository pacifistaPermissionRepository;

    @Test
    public void testRemoval() throws Exception {
        final PacifistaRole role = this.permissionComponentTest.createNewRole(false);
        final PacifistaPlayerRole playerRole = this.permissionComponentTest.createNewPlayerRole(role);
        final PacifistaPermission pacifistaPermission = this.permissionComponentTest.createPermission(role);
        final PacifistaPermission pacifistaPermission2 = this.permissionComponentTest.createPermission(role);

        super.sendDeleteRequest(ROUTE + "?id=" + role.getUuid(), status().isOk());

        Optional<PacifistaPlayerRole> playerSearch = playerRoleRepository.findById(playerRole.getId());
        if (playerSearch.isPresent()) {
            fail("PlayerRole present");
        }

        Optional<PacifistaPermission> searchPerm = this.pacifistaPermissionRepository.findById(pacifistaPermission.getId());
        if (searchPerm.isPresent()) {
            fail("Permission present");
        }
        searchPerm = this.pacifistaPermissionRepository.findById(pacifistaPermission2.getId());
        if (searchPerm.isPresent()) {
            fail("Permission2 present");
        }
    }

}
