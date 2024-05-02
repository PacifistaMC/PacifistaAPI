package fr.pacifista.api.server.essentials.client.commands_sender.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.server.essentials.client.commands_sender.dtos.CommandToSendDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "CommandToSendInternalClient",
        url = "${pacifista.api.server.essentials.app-domain-url}",
        path = CommandToSendClientImpl.INTERNAL_PATH
)
public interface CommandToSendInternalClient extends CrudClient<CommandToSendDTO> {
}
