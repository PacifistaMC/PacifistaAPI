package fr.pacifista.api.server.players.sync.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.enums.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerInventoryDataDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PlayerInventory",
        url = "${pacifista.api.app-domain-url}",
        path = "/playersync/inventory",
        configuration = FeignTokenInterceptor.class
)
public interface PlayerInventoryDataClient extends CrudClient<PlayerInventoryDataDTO> {
}