package fr.pacifista.api.client.players.players_sync.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerEnderchestDataDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PLayerEnderchestData", url = "${pacifista.api.app-domain-url}", path = "/playersync/enderchests")
public interface PlayerEnderchestDataClient extends CrudClient<PlayerEnderchestDataDTO> {
}
