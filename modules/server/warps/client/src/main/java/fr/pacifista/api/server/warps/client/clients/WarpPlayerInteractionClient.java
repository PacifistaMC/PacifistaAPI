package fr.pacifista.api.server.warps.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.warps.client.dtos.WarpPlayerInteractionDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "WarpPlayerInteractionClient",
        url = "${pacifista.api.server.warps.app-domain-url}",
        path = "/" + WarpPlayerInteractionClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface WarpPlayerInteractionClient extends CrudClient<WarpPlayerInteractionDTO> {
}
