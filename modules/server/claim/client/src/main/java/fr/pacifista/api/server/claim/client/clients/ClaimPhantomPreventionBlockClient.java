package fr.pacifista.api.server.claim.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.claim.client.dtos.ClaimPhantomPreventionBlockDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "ClaimPhantomPreventionBlockClient",
        url = "${pacifista.api.server.claim.app-domain-url}",
        path = ClaimPhantomPreventionBlockClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface ClaimPhantomPreventionBlockClient extends CrudClient<ClaimPhantomPreventionBlockDTO> {
}
