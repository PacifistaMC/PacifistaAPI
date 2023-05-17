package fr.pacifista.api.client.server.permissions.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.permissions.dtos.PacifistaRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PacifistaRole", url = "${pacifista.api.app-domain-url}", path = "/gameroles")
public interface PacifistaRoleClient extends CrudClient<PacifistaRoleDTO> {
}
