package fr.pacifista.api.server.players.sync.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.enums.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerEnderchestDataDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PLayerEnderchestData",
        url = "${pacifista.api.server.playersync.app-domain-url}",
        path = "/playersync/enderchests",
        configuration = FeignTokenInterceptor.class
)
public interface PlayerEnderchestDataClient extends CrudClient<PlayerEnderchestDataDTO> {
}
