package fr.pacifista.api.client.guilds.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.guilds.dtos.GuildDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Guilds", url = "${pacifista.api.app-domain-url}", path = "/guilds")
public interface GuildClient extends CrudClient<GuildDTO> {
}
