package fr.pacifista.api.client.players.players_sync.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerInventoryDataDTO;

public class PlayerInventoryDataImplClient extends FeignImpl<PlayerInventoryDataDTO, PlayerInventoryDataClient> implements PlayerInventoryDataClient {
    public PlayerInventoryDataImplClient() {
        super("playersync/inventory", PlayerInventoryDataClient.class);
    }
}
