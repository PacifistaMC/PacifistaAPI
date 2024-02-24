package fr.pacifista.api.server.guilds.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.guilds.client.dtos.GuildDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "Guilds",
        url = "${pacifista.api.server.guilds.app-domain-url}",
        path = "/guilds",
        configuration = FeignTokenInterceptor.class
)
public interface GuildClient extends CrudClient<GuildDTO> {
}
