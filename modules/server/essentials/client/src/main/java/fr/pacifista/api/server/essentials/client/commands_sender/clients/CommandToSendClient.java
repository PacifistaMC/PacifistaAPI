package fr.pacifista.api.server.essentials.client.commands_sender.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.essentials.client.commands_sender.dtos.CommandToSendDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "CommandToSendClient",
        url = "${pacifista.api.server.essentials.app-domain-url}",
        path = CommandToSendClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface CommandToSendClient extends CrudClient<CommandToSendDTO> {
}
