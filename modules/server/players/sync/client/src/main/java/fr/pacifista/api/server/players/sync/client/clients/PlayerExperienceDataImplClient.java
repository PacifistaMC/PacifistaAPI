package fr.pacifista.api.server.players.sync.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerExperienceDataDTO;

public class PlayerExperienceDataImplClient extends FeignImpl<PlayerExperienceDataDTO, PlayerExperienceDataClient> implements PlayerExperienceDataClient {
    public PlayerExperienceDataImplClient() {
        super("playersync/experience", PlayerExperienceDataClient.class);
    }
}
