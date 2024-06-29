package fr.pacifista.api.server.essentials.service.announcement.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.essentials.client.announcement.clients.AnnouncementClientImpl;
import fr.pacifista.api.server.essentials.client.announcement.dtos.AnnouncementDTO;
import fr.pacifista.api.server.essentials.service.announcement.services.AnnouncementService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + AnnouncementClientImpl.PATH)
public class AnnouncementResource extends ApiResource<AnnouncementDTO, AnnouncementService> {

    public AnnouncementResource(AnnouncementService service) {
        super(service);
    }

}
