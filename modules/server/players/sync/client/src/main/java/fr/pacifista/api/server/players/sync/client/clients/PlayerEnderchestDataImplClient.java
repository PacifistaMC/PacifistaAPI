package fr.pacifista.api.server.players.sync.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerEnderchestDataDTO;

public class PlayerEnderchestDataImplClient extends FeignImpl<PlayerEnderchestDataDTO, PlayerEnderchestDataClient> implements PlayerEnderchestDataClient {
    public PlayerEnderchestDataImplClient() {
        super("playersync/enderchests", PlayerEnderchestDataClient.class);
    }
}
