package fr.pacifista.api.client.server.guilds.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.guilds.dtos.GuildMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "GuildMessage", url = "${pacifista.api.app-domain-url}", path = "/guilds/messages")
public interface GuildMessageClient extends CrudClient<GuildMessageDTO> {
}
