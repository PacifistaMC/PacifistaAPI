package fr.pacifista.api.client.players.players_sync.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerExperienceDataDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PlayerExperienceData", url = "${pacifista.api.app-domain-url}", path = "/playersync/experience")
public interface PlayerExperienceDataClient extends CrudClient<PlayerExperienceDataDTO> {
}
