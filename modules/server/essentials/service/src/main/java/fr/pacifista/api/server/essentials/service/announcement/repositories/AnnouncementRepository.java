package fr.pacifista.api.server.essentials.service.announcement.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.essentials.service.announcement.entities.Announcement;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends ApiRepository<Announcement> {
}
