package fr.pacifista.api.client.server.players.players_sync.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.server.players.players_sync.dtos.PlayerMoneyDataDTO;

public class PlayerMoneyDataImplClient extends FeignImpl<PlayerMoneyDataDTO, PlayerMoneyDataClient> implements PlayerMoneyDataClient {
    public PlayerMoneyDataImplClient() {
        super("playersync/money", PlayerMoneyDataClient.class);
    }
}
