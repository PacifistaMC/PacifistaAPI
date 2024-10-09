package fr.pacifista.api.server.warps.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.warps.client.dtos.WarpPortalDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "WarpPortalClient",
        url = "${pacifista.api.server.warps.app-domain-url}",
        path = "/" + WarpPortalClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface WarpPortalClient extends CrudClient<WarpPortalDTO> {
}
