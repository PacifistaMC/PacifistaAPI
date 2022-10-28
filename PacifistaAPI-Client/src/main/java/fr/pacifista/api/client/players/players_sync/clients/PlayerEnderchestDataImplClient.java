package fr.pacifista.api.client.players.players_sync.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerEnderchestDataDTO;

public class PlayerEnderchestDataImplClient extends FeignImpl<PlayerEnderchestDataDTO, PlayerEnderchestDataClient> implements PlayerEnderchestDataClient {
    public PlayerEnderchestDataImplClient() {
        super("playersync/enderchests", PlayerEnderchestDataClient.class);
    }
}
