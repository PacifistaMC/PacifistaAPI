package fr.pacifista.api.server.essentials.service.command_sender.resources;


import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.essentials.client.commands_sender.clients.CommandToSendClientImpl;
import fr.pacifista.api.server.essentials.client.commands_sender.dtos.CommandToSendDTO;
import fr.pacifista.api.server.essentials.service.command_sender.services.CommandToSendService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + CommandToSendClientImpl.PATH)
public class CommandToSendResource extends ApiResource<CommandToSendDTO, CommandToSendService> {

    public CommandToSendResource(CommandToSendService service) {
        super(service);
    }

}
