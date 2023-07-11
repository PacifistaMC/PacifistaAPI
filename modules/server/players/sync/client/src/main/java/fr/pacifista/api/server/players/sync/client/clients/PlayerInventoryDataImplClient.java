package fr.pacifista.api.server.players.sync.client.clients;

import fr.pacifista.api.core.client.enums.clients.FeignImpl;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerInventoryDataDTO;

public class PlayerInventoryDataImplClient extends FeignImpl<PlayerInventoryDataDTO, PlayerInventoryDataClient> implements PlayerInventoryDataClient {
    public PlayerInventoryDataImplClient() {
        super("playersync/inventory", PlayerInventoryDataClient.class);
    }
}
