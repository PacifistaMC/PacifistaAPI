package fr.pacifista.api.server.permissions.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.permissions.client.dtos.PacifistaRoleDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaRole",
        url = "${pacifista.api.server.permissions.app-domain-url}",
        path = "/permissions/gameroles",
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaRoleClient extends CrudClient<PacifistaRoleDTO> {
}
