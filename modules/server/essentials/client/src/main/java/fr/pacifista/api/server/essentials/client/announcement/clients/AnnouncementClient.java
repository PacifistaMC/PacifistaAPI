package fr.pacifista.api.server.essentials.client.announcement.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.essentials.client.announcement.dtos.AnnouncementDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "AnnouncementClient",
        url = "${pacifista.api.server.essentials.app-domain-url}",
        path = AnnouncementClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface AnnouncementClient extends CrudClient<AnnouncementDTO> {
}
