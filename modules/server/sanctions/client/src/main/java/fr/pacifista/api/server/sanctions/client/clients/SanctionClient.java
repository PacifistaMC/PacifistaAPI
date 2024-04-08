package fr.pacifista.api.server.sanctions.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.sanctions.client.dtos.SanctionDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaSanction",
        url = "${pacifista.api.server.sanctions.app-domain-url}",
        configuration = FeignTokenInterceptor.class,
        path = "/sanctions"
)
public interface SanctionClient extends CrudClient<SanctionDTO> {
}
