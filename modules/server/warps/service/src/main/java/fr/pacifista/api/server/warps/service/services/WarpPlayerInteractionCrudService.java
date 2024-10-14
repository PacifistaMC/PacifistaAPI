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
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

        if (getRepository().findByWarpAndPlayerIdAndInteractionType(entity.getWarp(), entity.getPlayerId().toString(), WarpInteractionType.LIKE).isPresent()) {
            throw new ApiBadRequestException("L'interaction existe déjà.");
        }
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
        this.performUpdateOnWarps(entity, false);
    }

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<WarpPlayerInteraction> entity) {
        this.performUpdateOnWarps(entity, true);
    }

    private void performUpdateOnWarps(@NotNull Iterable<WarpPlayerInteraction> list, boolean isDelete) {
        final Set<Long> warpsToUpdate = new HashSet<>();
        final Map<Long, Integer> likesToRemove = new HashMap<>();
        final Map<Long, Integer> usesToRemove = new HashMap<>();

        Warp warp;
        for (final WarpPlayerInteraction interaction : list) {
            if (!isDelete && !this.isFreshCreation(interaction)) {
                continue;
            }

            warp = interaction.getWarp();
            warpsToUpdate.add(warp.getId());

            if (interaction.getInteractionType() == WarpInteractionType.LIKE) {
                likesToRemove.put(warp.getId(), likesToRemove.getOrDefault(warp.getId(), 0) + 1);
            } else if (interaction.getInteractionType() == WarpInteractionType.USE) {
                usesToRemove.put(warp.getId(), usesToRemove.getOrDefault(warp.getId(), 0) + 1);
            }
        }

        final Iterable<Warp> warps = this.warpCrudService.getRepository().findAllById(warpsToUpdate);
        for (final Warp w : warps) {

            if (isDelete) {
                w.setLikes(w.getLikes() - likesToRemove.getOrDefault(w.getId(), 0));
                w.setUses(w.getUses() - usesToRemove.getOrDefault(w.getId(), 0));
                if (w.getLikes() < 0) {
                    w.setLikes(0);
                }
                if (w.getUses() < 0) {
                    w.setUses(0);
                }
            } else {
                w.setLikes(w.getLikes() + likesToRemove.getOrDefault(w.getId(), 0));
                w.setUses(w.getUses() + usesToRemove.getOrDefault(w.getId(), 0));
            }

        }
        this.warpCrudService.getRepository().saveAll(warps);
    }

    private boolean isFreshCreation(@NonNull WarpPlayerInteraction interaction) {
        return interaction.getId() == null || interaction.getCreatedAt() == null || interaction.getCreatedAt().toInstant().plusSeconds(1).isAfter(Instant.now());
    }
}
