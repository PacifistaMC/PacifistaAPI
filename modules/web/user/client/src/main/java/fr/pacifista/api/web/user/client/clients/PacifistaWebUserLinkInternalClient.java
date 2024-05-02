package fr.pacifista.api.web.user.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaWebUserLinkInternalClient",
        url = "${pacifista.api.web.user.app-domain-url}",
        path = PacifistaWebUserLinkClientImpl.INTERNAL_PATH
)
public interface PacifistaWebUserLinkInternalClient extends CrudClient<PacifistaWebUserLinkDTO> {
}
