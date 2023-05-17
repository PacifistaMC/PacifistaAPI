package fr.pacifista.api.service.support.tickets.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.support.tickets.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.service.support.tickets.entities.PacifistaSupportTicket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PacifistaSupportTicketMapper.class})
public interface PacifistaSupportTicketMapper extends ApiMapper<PacifistaSupportTicket, PacifistaSupportTicketDTO> {
}
