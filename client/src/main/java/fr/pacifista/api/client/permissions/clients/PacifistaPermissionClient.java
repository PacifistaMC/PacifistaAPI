package fr.pacifista.api.client.permissions.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.permissions.dtos.PacifistaPermissionDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PacifistaPermission", url = "${pacifista.api.app-domain-url}", path = "/gamepermissions")
public interface PacifistaPermissionClient extends CrudClient<PacifistaPermissionDTO> {
}
