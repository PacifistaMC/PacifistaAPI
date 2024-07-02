package fr.pacifista.api.server.essentials.client.ingore_players.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.essentials.client.ingore_players.dtos.PlayerIgnoreDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PlayerIgnoreClient",
        url = "${pacifista.api.server.essentials.app-domain-url}",
        path = PlayerIgnoreClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface PlayerIgnoreClient extends CrudClient<PlayerIgnoreDTO> {
}
