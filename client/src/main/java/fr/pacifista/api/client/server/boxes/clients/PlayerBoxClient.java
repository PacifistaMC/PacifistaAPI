package fr.pacifista.api.client.boxes.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.boxes.dtos.PlayerBoxDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PlayerBox", url = "${pacifista.api.app-domain-url}", path = "/box/player")
public interface PlayerBoxClient extends CrudClient<PlayerBoxDTO> {
}
