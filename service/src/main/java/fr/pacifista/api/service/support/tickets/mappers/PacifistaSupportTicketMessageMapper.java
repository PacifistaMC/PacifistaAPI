package fr.pacifista.api.service.support.tickets.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketMessageDTO;
import fr.pacifista.api.service.support.tickets.entities.PacifistaSupportTicketMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PacifistaSupportTicketMapper.class)
public interface PacifistaSupportTicketMessageMapper extends ApiMapper<PacifistaSupportTicketMessage, PacifistaSupportTicketMessageDTO> {
}
