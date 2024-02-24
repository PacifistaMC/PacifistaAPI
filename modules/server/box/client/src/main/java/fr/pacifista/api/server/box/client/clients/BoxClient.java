package fr.pacifista.api.server.box.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.box.client.dtos.BoxDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "Box",
        url = "${pacifista.api.server.box.app-domain-url}",
        path = BoxImplClient.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface BoxClient extends CrudClient<BoxDTO> {
}
