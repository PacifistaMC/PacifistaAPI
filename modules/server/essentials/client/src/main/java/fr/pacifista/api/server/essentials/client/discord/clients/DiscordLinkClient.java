package fr.pacifista.api.server.essentials.client.discord.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.essentials.client.discord.dtos.DiscordLinkDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "HomeClient",
        url = "${pacifista.api.server.essentials.app-domain-url}",
        path = DiscordLinkClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface DiscordLinkClient extends CrudClient<DiscordLinkDTO> {
}
