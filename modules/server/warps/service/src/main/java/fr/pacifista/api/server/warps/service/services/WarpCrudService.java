package fr.pacifista.api.server.warps.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.warps.client.dtos.WarpConfigDTO;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import fr.pacifista.api.server.warps.service.entities.Warp;
import fr.pacifista.api.server.warps.service.entities.WarpConfig;
import fr.pacifista.api.server.warps.service.mappers.WarpConfigMapper;
import fr.pacifista.api.server.warps.service.mappers.WarpMapper;
import fr.pacifista.api.server.warps.service.repositories.WarpConfigRepository;
import fr.pacifista.api.server.warps.service.repositories.WarpPlayerInteractionRepository;
import fr.pacifista.api.server.warps.service.repositories.WarpPortalRepository;
import fr.pacifista.api.server.warps.service.repositories.WarpRepository;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void afterSavingEntity(@NonNull Iterable<Warp> entity) {
        List<WarpConfig> configs = this.findExistingConfigs(entity);

        WarpDTO warpDTO;
        WarpConfig warpConfig;
        for (final Warp warp : entity) {
            if (this.isWarpConfigExists(warp, configs)) {
                continue;
            }

            warpDTO = this.getMapper().toDto(warp);
            warpConfig = warpConfigMapper.toEntity(
                    WarpConfigDTO.initWithDefaults(warpDTO)
            );

            warpConfig.setWarp(warp);
            configs.add(warpConfig);
        }

        configs = warpConfigRepository.saveAll(configs);
        for (final Warp warp : entity) {
            for (final WarpConfig warpConfigAfterSave : configs) {
                if (warpConfigAfterSave.getWarp().getId().equals(warp.getId())) {
                    warp.setConfig(warpConfigAfterSave);
                    break;
                }
            }
        }
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

    private List<WarpConfig> findExistingConfigs(@NonNull Iterable<Warp> warps) {
        final List<Long> warpsIds = new ArrayList<>();

        for (final Warp warp : warps) {
            warpsIds.add(warp.getId());
        }

        return warpConfigRepository.findAllById(warpsIds);
    }

    private boolean isWarpConfigExists(@NonNull Warp warp, final Iterable<WarpConfig> warpConfigs) {
        for (final WarpConfig warpConfig : warpConfigs) {
            if (warpConfig.getWarp().getId().equals(warp.getId())) {
                return true;
            }
        }

        return false;
    }
}
