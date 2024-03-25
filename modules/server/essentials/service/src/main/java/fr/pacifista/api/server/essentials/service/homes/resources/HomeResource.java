package fr.pacifista.api.server.essentials.service.homes.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.essentials.client.homes.clients.HomeClientImpl;
import fr.pacifista.api.server.essentials.client.homes.dtos.HomeDTO;
import fr.pacifista.api.server.essentials.service.homes.services.HomeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + HomeClientImpl.PATH)
public class HomeResource extends ApiResource<HomeDTO, HomeService> {
    public HomeResource(HomeService service) {
        super(service);
    }
}
