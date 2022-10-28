package fr.pacifista.api.client.permissions.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.permissions.dtos.PacifistaRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PacifistaRole", url = "${pacifista.api.app-domain-url}", path = "/gameroles")
public interface PacifistaRoleClient extends CrudClient<PacifistaRoleDTO> {
}
