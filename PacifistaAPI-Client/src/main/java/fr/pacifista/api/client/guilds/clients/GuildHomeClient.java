package fr.pacifista.api.client.guilds.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.guilds.dtos.GuildHomeDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GuildsHome", url = "${pacifista.api.app-domain-url}", path = "/guilds/homes")
public interface GuildHomeClient extends CrudClient<GuildHomeDTO> {
}
