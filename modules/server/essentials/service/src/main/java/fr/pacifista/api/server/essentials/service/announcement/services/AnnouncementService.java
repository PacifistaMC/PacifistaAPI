package fr.pacifista.api.server.essentials.service.announcement.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.essentials.client.announcement.dtos.AnnouncementDTO;
import fr.pacifista.api.server.essentials.service.announcement.entities.Announcement;
import fr.pacifista.api.server.essentials.service.announcement.mappers.AnnouncementMapper;
import fr.pacifista.api.server.essentials.service.announcement.repositories.AnnouncementRepository;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementService extends ApiService<AnnouncementDTO, Announcement, AnnouncementMapper, AnnouncementRepository> {

    public AnnouncementService(AnnouncementMapper mapper, AnnouncementRepository repository) {
        super(repository, mapper);
    }

}
