package fr.pacifista.api.service.boxes.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.modules.boxes.dtos.BoxRewardDTO;
import fr.pacifista.api.service.boxes.entities.Box;
import fr.pacifista.api.service.boxes.entities.BoxReward;
import fr.pacifista.api.service.boxes.mappers.BoxRewardMapper;
import fr.pacifista.api.service.boxes.repositories.BoxRepository;
import fr.pacifista.api.service.boxes.repositories.BoxRewardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public BoxRewardDTO create(BoxRewardDTO request) {
        final Box box = findBoxByRequest(request);
        final BoxReward boxReward = super.getMapper().toEntity(request);

        boxReward.setBox(box);
        return super.getMapper().toDto(super.getRepository().save(boxReward));
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
