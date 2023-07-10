package fr.pacifista.api.server.guilds.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.enums.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.guilds.client.dtos.GuildExperienceDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "GuildsExperience",
        url = "${pacifista.api.server.guilds.app-domain-url}",
        path = "/guilds/exp",
        configuration = FeignTokenInterceptor.class
)
public interface GuildExperienceClient extends CrudClient<GuildExperienceDTO> {
}
