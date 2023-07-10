package fr.pacifista.api.server.players.sync.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.enums.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerExperienceDataDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PlayerExperienceData",
        url = "${pacifista.api.app-domain-url}",
        path = "/playersync/experience",
        configuration = FeignTokenInterceptor.class
)
public interface PlayerExperienceDataClient extends CrudClient<PlayerExperienceDataDTO> {
}
