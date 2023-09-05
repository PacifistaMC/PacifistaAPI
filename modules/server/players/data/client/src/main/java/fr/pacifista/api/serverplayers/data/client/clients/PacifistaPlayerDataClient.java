package fr.pacifista.api.serverplayers.data.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.enums.clients.FeignTokenInterceptor;
import fr.pacifista.api.serverplayers.data.client.dtos.PacifistaPlayerDataDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaPlayerDataClient",
        url = "${pacifista.api.server.playerdata.app-domain-url}",
        path = "/playerdata/data",
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaPlayerDataClient extends CrudClient<PacifistaPlayerDataDTO> {
}
