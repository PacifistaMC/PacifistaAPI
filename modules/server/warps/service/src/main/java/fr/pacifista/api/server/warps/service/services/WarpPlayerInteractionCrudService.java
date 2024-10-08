package fr.pacifista.api.server.warps.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.server.warps.client.dtos.WarpPlayerInteractionDTO;
import fr.pacifista.api.server.warps.client.enums.WarpInteractionType;
import fr.pacifista.api.server.warps.service.entities.Warp;
import fr.pacifista.api.server.warps.service.entities.WarpPlayerInteraction;
import fr.pacifista.api.server.warps.service.mappers.WarpPlayerInteractionMapper;
import fr.pacifista.api.server.warps.service.repositories.WarpPlayerInteractionRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class WarpPlayerInteractionCrudService extends ApiService<WarpPlayerInteractionDTO, WarpPlayerInteraction, WarpPlayerInteractionMapper, WarpPlayerInteractionRepository> {

    private final WarpCrudService warpCrudService;

    public WarpPlayerInteractionCrudService(WarpPlayerInteractionRepository repository,
                                            WarpPlayerInteractionMapper mapper,
                                            WarpCrudService warpCrudService) {
        super(repository, mapper);
        this.warpCrudService = warpCrudService;
    }

    @Override
    public void afterMapperCall(@NonNull WarpPlayerInteractionDTO dto, @NonNull WarpPlayerInteraction entity) {
        entity.setWarp(warpCrudService.getWarp(dto.getWarp().getId()));
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<WarpPlayerInteraction> entity) {
        for (final WarpPlayerInteraction interaction : entity) {
            if (interaction.getId() != null) {
                throw new ApiBadRequestException("La modification d'une interaction n'est pas autorisée.");
            }
        }
    }

    @Override
    public void afterSavingEntity(@NonNull Iterable<WarpPlayerInteraction> entity) {
        final Set<Long> warpsToUpdate = new HashSet<>();
        final Map<Long, Integer> likesToAdd = new HashMap<>();
        final Map<Long, Integer> usesToAdd = new HashMap<>();

        Warp warp;
        for (final WarpPlayerInteraction interaction : entity) {
            warp = interaction.getWarp();
            warpsToUpdate.add(warp.getId());

            if (interaction.getInteractionType() == WarpInteractionType.LIKE) {
                likesToAdd.put(warp.getId(), likesToAdd.getOrDefault(warp.getId(), 0) + 1);
            } else if (interaction.getInteractionType() == WarpInteractionType.USE) {
                usesToAdd.put(warp.getId(), usesToAdd.getOrDefault(warp.getId(), 0) + 1);
            }
        }

        final Iterable<Warp> warps = this.warpCrudService.getRepository().findAllById(warpsToUpdate);
        for (final Warp w : warps) {
            w.setLikes(w.getLikes() + likesToAdd.getOrDefault(w.getId(), 0));
            w.setUses(w.getUses() + usesToAdd.getOrDefault(w.getId(), 0));
        }
        this.warpCrudService.getRepository().saveAll(warps);
    }

}
