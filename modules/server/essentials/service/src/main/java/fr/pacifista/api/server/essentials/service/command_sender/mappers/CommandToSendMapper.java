package fr.pacifista.api.server.essentials.service.command_sender.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.essentials.client.commands_sender.dtos.CommandToSendDTO;
import fr.pacifista.api.server.essentials.service.command_sender.entities.CommandToSend;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandToSendMapper extends ApiMapper<CommandToSend, CommandToSendDTO> {
}
