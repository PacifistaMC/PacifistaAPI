package fr.pacifista.api.client.server.boxes.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.boxes.dtos.BoxDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Box", url = "${pacifista.api.app-domain-url}", path = "/box")
public interface BoxClient extends CrudClient<BoxDTO> {
}
