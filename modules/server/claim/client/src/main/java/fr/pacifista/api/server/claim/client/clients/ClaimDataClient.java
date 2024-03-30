package fr.pacifista.api.server.claim.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "ClaimDataClient",
        url = "${pacifista.api.server.claim.app-domain-url}",
        path = ClaimDataClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface ClaimDataClient extends CrudClient<ClaimDataDTO> {
}
