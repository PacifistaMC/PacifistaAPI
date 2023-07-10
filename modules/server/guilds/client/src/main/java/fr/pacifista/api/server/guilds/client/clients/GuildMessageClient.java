package fr.pacifista.api.server.guilds.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.enums.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.guilds.client.dtos.GuildMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "GuildMessage",
        url = "${pacifista.api.server.guilds.app-domain-url}",
        path = "/guilds/messages",
        configuration = FeignTokenInterceptor.class
)
public interface GuildMessageClient extends CrudClient<GuildMessageDTO> {
}
