package fr.pacifista.api.server.players.data.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerDataDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaPlayerDataInternalClient",
        url = "${pacifista.api.server.playerdata.app-domain-url}",
        path = PacifistaPlayerDataImplClient.INTERNAL_PATH
)
public interface PacifistaPlayerDataInternalClient extends CrudClient<PacifistaPlayerDataDTO> {
}
