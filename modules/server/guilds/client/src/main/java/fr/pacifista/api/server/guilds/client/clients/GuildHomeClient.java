package fr.pacifista.api.server.guilds.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.guilds.client.dtos.GuildHomeDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "GuildsHome",
        url = "${pacifista.api.server.guilds.app-domain-url}",
        path = "/guilds/homes",
        configuration = FeignTokenInterceptor.class
)
public interface GuildHomeClient extends CrudClient<GuildHomeDTO> {
}
