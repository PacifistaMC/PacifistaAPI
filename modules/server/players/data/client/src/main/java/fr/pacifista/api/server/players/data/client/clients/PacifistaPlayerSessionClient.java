package fr.pacifista.api.server.players.data.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerSessionDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaPlayerSessionClient",
        url = "${pacifista.api.server.playerdata.app-domain-url}",
        path = "/playerdata/session",
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaPlayerSessionClient extends CrudClient<PacifistaPlayerSessionDTO> {
}
