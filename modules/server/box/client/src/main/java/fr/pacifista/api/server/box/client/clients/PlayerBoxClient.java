package fr.pacifista.api.server.box.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.server.box.client.dtos.PlayerBoxDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PlayerBox", url = "${pacifista.api.app-domain-url}", path = PlayerBoxImplClient.PATH)
public interface PlayerBoxClient extends CrudClient<PlayerBoxDTO> {
}
