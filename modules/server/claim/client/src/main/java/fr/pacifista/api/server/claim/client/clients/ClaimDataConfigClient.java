package fr.pacifista.api.server.claim.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataConfigDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "ClaimDataConfigClient",
        url = "${pacifista.api.server.claim.app-domain-url}",
        path = ClaimDataConfigClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface ClaimDataConfigClient extends CrudClient<ClaimDataConfigDTO> {
}
