package fr.pacifista.api.client.server.permissions.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.permissions.dtos.PacifistaPermissionDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PacifistaPermission", url = "${pacifista.api.app-domain-url}", path = "/gamepermissions")
public interface PacifistaPermissionClient extends CrudClient<PacifistaPermissionDTO> {
}
