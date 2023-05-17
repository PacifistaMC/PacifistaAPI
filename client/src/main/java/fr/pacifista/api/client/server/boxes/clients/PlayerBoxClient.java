package fr.pacifista.api.client.server.boxes.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.boxes.dtos.PlayerBoxDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PlayerBox", url = "${pacifista.api.app-domain-url}", path = "/box/player")
public interface PlayerBoxClient extends CrudClient<PlayerBoxDTO> {
}
