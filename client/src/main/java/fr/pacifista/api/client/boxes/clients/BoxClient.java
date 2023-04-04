package fr.pacifista.api.client.boxes.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.boxes.dtos.BoxDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Box", url = "${pacifista.api.app-domain-url}", path = "/box")
public interface BoxClient extends CrudClient<BoxDTO> {
}
