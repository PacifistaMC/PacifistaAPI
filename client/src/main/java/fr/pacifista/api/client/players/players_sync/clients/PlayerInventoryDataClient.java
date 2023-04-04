package fr.pacifista.api.client.players.players_sync.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerInventoryDataDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PlayerInventory", url = "${pacifista.api.app-domain-url}", path = "/playersync/inventory")
public interface PlayerInventoryDataClient extends CrudClient<PlayerInventoryDataDTO> {
}
