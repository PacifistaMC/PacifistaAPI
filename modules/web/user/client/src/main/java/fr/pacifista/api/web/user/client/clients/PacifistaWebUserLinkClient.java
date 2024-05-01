package fr.pacifista.api.web.user.client.clients;


import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "CommandToSendClient",
        url = "${pacifista.api.web.user.app-domain-url}",
        path = PacifistaWebUserLinkClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaWebUserLinkClient extends CrudClient<PacifistaWebUserLinkDTO> {
}
