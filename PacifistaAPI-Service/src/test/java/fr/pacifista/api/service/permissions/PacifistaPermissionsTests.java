package fr.pacifista.api.service.permissions;

import fr.pacifista.api.client.modules.permissions.dtos.PacifistaPermissionDTO;
import fr.pacifista.api.service.permissions.entities.PacifistaRole;
import fr.pacifista.api.service.utils.PacifistaServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PacifistaPermissionsTests extends PacifistaServiceTest {

    @Autowired
    private PacifistaPermissionComponentTest permissionComponentTest;

    private static final String ROUTE = "/gamepermissions";

    @Test
    public void testPermissionCreationSuccess() throws Exception {
        final PacifistaRole role = permissionComponentTest.createNewRole(false);

        final PacifistaPermissionDTO request = new PacifistaPermissionDTO();
        request.setPermission("test.data");
        request.setRoleId(role.getUuid());

        final PacifistaPermissionDTO response = super.sendPostRequest(
                ROUTE,
                request,
                status().isOk(),
                PacifistaPermissionDTO.class
        );

        assertEquals(request.getPermission(), response.getPermission());
        assertEquals(request.getRoleId(), response.getRoleId());
    }

    @Test
    public void testPermissionCreationFailNoId() throws Exception {
        final PacifistaPermissionDTO request = new PacifistaPermissionDTO();
        request.setPermission("test.data");

        super.getMockMvc().perform(patch(ROUTE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer tokentest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.getJsonHelper().toJson(request))
        ).andExpect(status().is4xxClientError());
    }

}
