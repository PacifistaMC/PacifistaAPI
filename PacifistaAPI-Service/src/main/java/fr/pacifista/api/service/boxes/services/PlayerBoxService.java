package fr.pacifista.api.service.boxes.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.boxes.dtos.PlayerBoxDTO;
import fr.pacifista.api.service.boxes.entities.Box;
import fr.pacifista.api.service.boxes.entities.PlayerBox;
import fr.pacifista.api.service.boxes.mappers.PlayerBoxMapper;
import fr.pacifista.api.service.boxes.repositories.BoxRepository;
import fr.pacifista.api.service.boxes.repositories.PlayerBoxRepository;
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
    public void beforeSavingEntity(@NonNull PlayerBoxDTO request, @NonNull PlayerBox entity) {
        if (request.getId() == null) {
            final Box box = findBoxByRequest(request);
            entity.setBox(box);
        }
    }

    public void give(final PlayerBoxDTO request) {
        final Box box = findBoxByRequest(request);
        final Optional<PlayerBox> search = getRepository().findPlayerBoxByBoxAndPlayerUuid(box, request.getPlayerUuid().toString());

        if (search.isPresent()) {
            final PlayerBox playerBox = search.get();

            playerBox.setAmount(playerBox.getAmount() + request.getAmount());
            getRepository().save(playerBox);
        } else {
            super.create(request);
        }
    }

    public void take(final PlayerBoxDTO request) {
        final Box box = findBoxByRequest(request);
        final Optional<PlayerBox> search = getRepository().findPlayerBoxByBoxAndPlayerUuid(box, request.getPlayerUuid().toString());

        if (search.isPresent()) {
            final PlayerBox playerBox = search.get();

            if (playerBox.getAmount() <= request.getAmount()) {
                getRepository().delete(playerBox);
            } else {
                playerBox.setAmount(playerBox.getAmount() - request.getAmount());
                getRepository().save(playerBox);
            }
        }
    }

    private Box findBoxByRequest(final PlayerBoxDTO request) {
        if (request.getBox().getId() == null) {
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
