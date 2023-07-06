package fr.pacifista.api.server.box.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.box.client.dtos.BoxRewardDTO;
import fr.pacifista.api.server.box.service.entities.Box;
import fr.pacifista.api.server.box.service.entities.BoxReward;
import fr.pacifista.api.server.box.service.mappers.BoxRewardMapper;
import fr.pacifista.api.server.box.service.repositories.BoxRepository;
import fr.pacifista.api.server.box.service.repositories.BoxRewardRepository;
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
