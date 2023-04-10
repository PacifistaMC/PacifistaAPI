package fr.pacifista.api.client.permissions.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.permissions.dtos.PacifistaPlayerRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PacifistaPlayerRoles", url = "${pacifista.api.app-domain-url}", path = "/gameroles/player")
public interface PacifistaPlayerRolesClient extends CrudClient<PacifistaPlayerRoleDTO> {
}
