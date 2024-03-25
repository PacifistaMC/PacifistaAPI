package fr.pacifista.api.server.essentials.service.homes.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.essentials.client.homes.dtos.HomeDTO;
import fr.pacifista.api.server.essentials.service.homes.entities.Home;
import fr.pacifista.api.server.essentials.service.homes.mappers.HomeMapper;
import fr.pacifista.api.server.essentials.service.homes.repositories.HomeRepository;
import org.springframework.stereotype.Service;

@Service
public class HomeService extends ApiService<HomeDTO, Home, HomeMapper, HomeRepository> {

    public HomeService(HomeRepository repository, HomeMapper mapper) {
        super(repository, mapper);
    }

}
