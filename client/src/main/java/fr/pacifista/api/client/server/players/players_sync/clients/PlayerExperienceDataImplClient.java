package fr.pacifista.api.client.players.players_sync.clients;

import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerExperienceDataDTO;

public class PlayerExperienceDataImplClient extends FeignImpl<PlayerExperienceDataDTO, PlayerExperienceDataClient> implements PlayerExperienceDataClient {
    public PlayerExperienceDataImplClient() {
        super("playersync/experience", PlayerExperienceDataClient.class);
    }
}
