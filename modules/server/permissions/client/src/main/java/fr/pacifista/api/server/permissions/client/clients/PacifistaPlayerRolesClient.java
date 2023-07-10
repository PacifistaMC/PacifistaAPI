package fr.pacifista.api.server.permissions.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.enums.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaPlayerRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaPlayerRoles",
        url = "${pacifista.api.server.permissions.app-domain-url}",
        path = "/permissions/gameroles/player",
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaPlayerRolesClient extends CrudClient<PacifistaPlayerRoleDTO> {
}
