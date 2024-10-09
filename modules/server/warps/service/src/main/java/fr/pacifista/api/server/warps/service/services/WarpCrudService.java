package fr.pacifista.api.server.warps.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import fr.pacifista.api.server.warps.service.entities.Warp;
import fr.pacifista.api.server.warps.service.mappers.WarpConfigMapper;
import fr.pacifista.api.server.warps.service.mappers.WarpMapper;
import fr.pacifista.api.server.warps.service.repositories.WarpConfigRepository;
import fr.pacifista.api.server.warps.service.repositories.WarpPlayerInteractionRepository;
import fr.pacifista.api.server.warps.service.repositories.WarpPortalRepository;
import fr.pacifista.api.server.warps.service.repositories.WarpRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WarpCrudService extends ApiService<WarpDTO, Warp, WarpMapper, WarpRepository> {

    private final WarpConfigMapper warpConfigMapper;

    private final WarpConfigRepository warpConfigRepository;
    private final WarpPlayerInteractionRepository warpPlayerInteractionRepository;
    private final WarpPortalRepository warpPortalRepository;

    public WarpCrudService(WarpRepository repository,
                           WarpMapper mapper,
                           WarpConfigRepository warpConfigRepository,
                           WarpConfigMapper warpConfigMapper,
                           WarpPlayerInteractionRepository warpPlayerInteractionRepository,
                           WarpPortalRepository warpPortalRepository) {
        super(repository, mapper);
        this.warpConfigRepository = warpConfigRepository;
        this.warpConfigMapper = warpConfigMapper;
        this.warpPlayerInteractionRepository = warpPlayerInteractionRepository;
        this.warpPortalRepository = warpPortalRepository;
    }

    @Override
    @Transactional
    public @NonNull WarpDTO create(WarpDTO request) {
        final WarpDTO warpDTO = super.create(request);

        if(warpDTO.getConfig() == null) {
            warpDTO.setConfig(this.warpC);
        }
        return warpDTO;
    }

    @Override
    @Transactional
    public List<WarpDTO> create(List<@Valid WarpDTO> request) {
        final List<WarpDTO> res = super.create(request);
    }

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<Warp> entity) {
        this.warpPortalRepository.deleteAllByWarpIn(entity);
        this.warpPlayerInteractionRepository.deleteAllByWarpIn(entity);
        this.warpConfigRepository.deleteAllByWarpIn(entity);
    }

    @NotNull
    protected Warp getWarp(UUID warpId) {
        if (warpId == null) {
            throw new ApiBadRequestException("L'id du warp est requis");
        }

        final Warp warp = getRepository().findByUuid(warpId.toString()).orElse(null);
        if (warp == null) {
            throw new ApiNotFoundException("Le warp avec l'id " + warpId + " n'existe pas");
        }
        return warp;
    }

}
