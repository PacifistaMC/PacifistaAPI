package fr.pacifista.api.client.guilds.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.guilds.dtos.GuildExperienceDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GuildsExperience", url = "${pacifista.api.app-domain-url}", path = "/guilds/exp")
public interface GuildExperienceClient extends CrudClient<GuildExperienceDTO> {
}
