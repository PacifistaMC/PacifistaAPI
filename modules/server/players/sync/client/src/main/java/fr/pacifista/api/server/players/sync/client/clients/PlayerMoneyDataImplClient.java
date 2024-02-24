package fr.pacifista.api.server.players.sync.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerMoneyDataDTO;

public class PlayerMoneyDataImplClient extends FeignImpl<PlayerMoneyDataDTO, PlayerMoneyDataClient> implements PlayerMoneyDataClient {
    public PlayerMoneyDataImplClient() {
        super("playersync/money", PlayerMoneyDataClient.class);
    }
}
