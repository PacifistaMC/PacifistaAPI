package fr.pacifista.api.serverplayers.data.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.serverplayers.data.client.dtos.PacifistaPlayerChatMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaPlayerChatMessageClient",
        url = "${pacifista.api.server.playerdata.app-domain-url}",
        path = "/playerdata/chatmessages",
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaPlayerChatMessageClient extends CrudClient<PacifistaPlayerChatMessageDTO> {
}
