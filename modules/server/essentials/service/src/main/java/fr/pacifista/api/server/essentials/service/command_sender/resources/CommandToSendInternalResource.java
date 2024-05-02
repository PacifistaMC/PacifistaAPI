package fr.pacifista.api.server.essentials.service.command_sender.resources;

import fr.pacifista.api.server.essentials.client.commands_sender.clients.CommandToSendClientImpl;
import fr.pacifista.api.server.essentials.service.command_sender.services.CommandToSendService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + CommandToSendClientImpl.INTERNAL_PATH)
public class CommandToSendInternalResource extends CommandToSendResource {
    public CommandToSendInternalResource(CommandToSendService service) {
        super(service);
    }
}
