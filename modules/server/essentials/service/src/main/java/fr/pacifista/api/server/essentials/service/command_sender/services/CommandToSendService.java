package fr.pacifista.api.server.essentials.service.command_sender.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.essentials.client.commands_sender.dtos.CommandToSendDTO;
import fr.pacifista.api.server.essentials.service.command_sender.entities.CommandToSend;
import fr.pacifista.api.server.essentials.service.command_sender.mappers.CommandToSendMapper;
import fr.pacifista.api.server.essentials.service.command_sender.repositories.CommandToSendRepository;
import org.springframework.stereotype.Service;

@Service
public class CommandToSendService extends ApiService<CommandToSendDTO, CommandToSend, CommandToSendMapper, CommandToSendRepository> {

    public CommandToSendService(CommandToSendRepository repository, CommandToSendMapper mapper) {
        super(repository, mapper);
    }

}
