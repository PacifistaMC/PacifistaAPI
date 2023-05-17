package fr.pacifista.api.client.server.guilds.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.guilds.dtos.GuildHomeDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GuildsHome", url = "${pacifista.api.app-domain-url}", path = "/guilds/homes")
public interface GuildHomeClient extends CrudClient<GuildHomeDTO> {
}
