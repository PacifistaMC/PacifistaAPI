package fr.pacifista.api.server.permissions.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.enums.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaPermissionDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaPermission",
        url = "${pacifista.api.server.permissions.app-domain-url}",
        path = "/permissions//gamepermissions",
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaPermissionClient extends CrudClient<PacifistaPermissionDTO> {
}
