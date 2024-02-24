package fr.pacifista.api.server.warps.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "Warps",
        url = "${pacifista.api.server.warps.app-domain-url}",
        path = "/warps",
        configuration = FeignTokenInterceptor.class
)
public interface WarpClient extends CrudClient<WarpDTO> {
}
