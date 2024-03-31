package fr.pacifista.api.server.claim.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataUserDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "ClaimDataUserClient",
        url = "${pacifista.api.server.claim.app-domain-url}",
        path = ClaimDataUserClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface ClaimDataUserClient extends CrudClient<ClaimDataUserDTO> {
}
