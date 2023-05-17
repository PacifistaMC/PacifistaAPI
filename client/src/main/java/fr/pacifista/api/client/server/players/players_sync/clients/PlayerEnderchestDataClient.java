package fr.pacifista.api.client.server.players.players_sync.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.players.players_sync.dtos.PlayerEnderchestDataDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PLayerEnderchestData", url = "${pacifista.api.app-domain-url}", path = "/playersync/enderchests")
public interface PlayerEnderchestDataClient extends CrudClient<PlayerEnderchestDataDTO> {
}
