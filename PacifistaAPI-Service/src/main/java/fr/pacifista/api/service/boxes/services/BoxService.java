package fr.pacifista.api.service.boxes.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.modules.boxes.dtos.BoxDTO;
import fr.pacifista.api.service.boxes.entities.Box;
import fr.pacifista.api.service.boxes.mappers.BoxMapper;
import fr.pacifista.api.service.boxes.repositories.BoxRepository;
import org.springframework.stereotype.Service;

@Service
public class BoxService extends ApiService<BoxDTO, Box, BoxMapper, BoxRepository> {

    public BoxService(BoxRepository repository,
                      BoxMapper mapper) {
        super(repository, mapper);
    }

}
