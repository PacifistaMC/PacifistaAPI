package fr.pacifista.api.server.essentials.service.announcement.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.essentials.client.announcement.dtos.AnnouncementDTO;
import fr.pacifista.api.server.essentials.service.announcement.entities.Announcement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper extends ApiMapper<Announcement, AnnouncementDTO> {
}
