package fr.pacifista.api.service.server.boxes.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.server.boxes.dtos.PlayerBoxDTO;
import fr.pacifista.api.service.server.boxes.entities.Box;
import fr.pacifista.api.service.server.boxes.entities.PlayerBox;
import fr.pacifista.api.service.server.boxes.mappers.PlayerBoxMapper;
import fr.pacifista.api.service.server.boxes.repositories.BoxRepository;
import fr.pacifista.api.service.server.boxes.repositories.PlayerBoxRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerBoxService extends ApiService<PlayerBoxDTO, PlayerBox, PlayerBoxMapper, PlayerBoxRepository> {

    private final BoxRepository boxRepository;

    public PlayerBoxService(PlayerBoxRepository repository,
                            PlayerBoxMapper mapper,
                            BoxRepository boxRepository) {
        super(repository, mapper);
        this.boxRepository = boxRepository;
    }

    @Override
    public void afterMapperCall(@NonNull PlayerBoxDTO dto, @NonNull PlayerBox entity) {
        if (dto.getId() == null) {
            final Box box = findBoxByRequest(dto);
            entity.setBox(box);
        }
    }

    private Box findBoxByRequest(final PlayerBoxDTO request) {
        if (request.getBox() == null || request.getBox().getId() == null) {
            throw new ApiBadRequestException("Il manque l'id de la box.");
        }

        final Optional<Box> search = boxRepository.findByUuid(request.getBox().getId().toString());
        if (search.isPresent()) {
            return search.get();
        } else {
            throw new ApiNotFoundException("La box id n'existe pas.");
        }
    }

}
