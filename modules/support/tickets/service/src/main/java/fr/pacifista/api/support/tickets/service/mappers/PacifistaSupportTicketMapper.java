package fr.pacifista.api.support.tickets.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.support.tickets.client.dtos.PacifistaSupportTicketDTO;
import fr.pacifista.api.support.tickets.service.entities.PacifistaSupportTicket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacifistaSupportTicketMapper extends ApiMapper<PacifistaSupportTicket, PacifistaSupportTicketDTO> {
}
