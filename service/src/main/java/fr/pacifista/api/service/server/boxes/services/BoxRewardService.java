package fr.pacifista.api.service.server.boxes.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.server.boxes.dtos.BoxRewardDTO;
import fr.pacifista.api.service.server.boxes.entities.Box;
import fr.pacifista.api.service.server.boxes.entities.BoxReward;
import fr.pacifista.api.service.server.boxes.mappers.BoxRewardMapper;
import fr.pacifista.api.service.server.boxes.repositories.BoxRepository;
import fr.pacifista.api.service.server.boxes.repositories.BoxRewardRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoxRewardService extends ApiService<BoxRewardDTO, BoxReward, BoxRewardMapper, BoxRewardRepository> {

    private final BoxRepository boxRepository;

    public BoxRewardService(BoxRewardRepository repository,
                            BoxRepository boxRepository,
                            BoxRewardMapper mapper) {
        super(repository, mapper);
        this.boxRepository = boxRepository;
    }

    @Override
    public void afterMapperCall(@NonNull BoxRewardDTO dto, @NonNull BoxReward entity) {
        if (dto.getId() == null) {
            final Box box = findBoxByRequest(dto);
            entity.setBox(box);
        }
    }

    private Box findBoxByRequest(final BoxRewardDTO request) {
        if (request.getBoxId() == null) {
            throw new ApiBadRequestException("Il manque la box id pour créer une récompense.");
        }

        final Optional<Box> search = boxRepository.findByUuid(request.getBoxId().toString());
        if (search.isPresent()) {
            return search.get();
        } else {
            throw new ApiNotFoundException("La box id n'existe pas.");
        }
    }
}
