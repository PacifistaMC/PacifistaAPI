package fr.pacifista.api.client.server.guilds.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.guilds.dtos.GuildExperienceDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GuildsExperience", url = "${pacifista.api.app-domain-url}", path = "/guilds/exp")
public interface GuildExperienceClient extends CrudClient<GuildExperienceDTO> {
}
