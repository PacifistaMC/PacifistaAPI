package fr.pacifista.api.service.server.boxes.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.client.server.boxes.dtos.BoxDTO;
import fr.pacifista.api.service.server.boxes.entities.Box;
import fr.pacifista.api.service.server.boxes.entities.PlayerBox;
import fr.pacifista.api.service.server.boxes.mappers.BoxMapper;
import fr.pacifista.api.service.server.boxes.repositories.BoxRepository;
import fr.pacifista.api.service.server.boxes.repositories.PlayerBoxRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void beforeDeletingEntity(@NonNull Iterable<Box> entity) {
        final List<PlayerBox> playerBoxes = new ArrayList<>();

        for (final Box box : entity) {
            playerBoxes.addAll(playerBoxRepository.findAllByBox(box));
        }
        playerBoxRepository.deleteAll(playerBoxes);
    }

}
