package fr.pacifista.api.server.claim.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.claim.client.dtos.UserClaimAmountDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "UserClaimAmountClient",
        url = "${pacifista.api.server.claim.app-domain-url}",
        path = UserClaimAmountClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface UserClaimAmountClient extends CrudClient<UserClaimAmountDTO> {
}
