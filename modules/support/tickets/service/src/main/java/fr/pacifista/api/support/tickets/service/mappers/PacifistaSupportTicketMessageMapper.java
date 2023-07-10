package fr.pacifista.api.support.tickets.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketMessageDTO;
import fr.pacifista.api.support.tickets.service.entities.PacifistaSupportTicketMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PacifistaSupportTicketMapper.class)
public interface PacifistaSupportTicketMessageMapper extends ApiMapper<PacifistaSupportTicketMessage, PacifistaSupportTicketMessageDTO> {
}
