package fr.pacifista.api.server.box.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.server.box.client.dtos.BoxDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Box", url = "${pacifista.api.server.box.app-domain-url}", path = BoxImplClient.PATH)
public interface BoxClient extends CrudClient<BoxDTO> {
}
