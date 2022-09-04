package fr.pacifista.api.service.boxes.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.boxes.dtos.BoxDTO;
import fr.pacifista.api.service.boxes.entities.Box;
import fr.pacifista.api.service.boxes.mappers.BoxMapper;
import fr.pacifista.api.service.boxes.repositories.BoxRepository;
import fr.pacifista.api.service.boxes.repositories.PlayerBoxRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoxService extends ApiService<BoxDTO, Box, BoxMapper, BoxRepository> {

    private final PlayerBoxRepository playerBoxRepository;

    public BoxService(BoxRepository repository,
                      BoxMapper mapper,
                      PlayerBoxRepository playerBoxRepository) {
        super(repository, mapper);
        this.playerBoxRepository = playerBoxRepository;
    }

    @Override
    public void delete(String id) {
        playerBoxRepository.deleteAll(playerBoxRepository.findAllByBox(findBoxById(id)));

        super.delete(id);
    }

    @Override
    public void delete(String... ids) {
        for (final String id : ids) {
            playerBoxRepository.deleteAll(playerBoxRepository.findAllByBox(findBoxById(id)));
        }

        super.delete(ids);
    }

    private Box findBoxById(String id) {
        final Optional<Box> search = super.getRepository().findByUuid(id);

        if (search.isPresent()) {
            return search.get();
        } else {
            throw new ApiNotFoundException(String.format("La box id %s n'existe pas.", id));
        }
    }

}
