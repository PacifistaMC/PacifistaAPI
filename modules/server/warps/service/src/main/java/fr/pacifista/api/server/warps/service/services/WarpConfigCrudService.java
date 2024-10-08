package fr.pacifista.api.server.warps.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.warps.client.dtos.WarpConfigDTO;
import fr.pacifista.api.server.warps.service.entities.Warp;
import fr.pacifista.api.server.warps.service.entities.WarpConfig;
import fr.pacifista.api.server.warps.service.mappers.WarpConfigMapper;
import fr.pacifista.api.server.warps.service.repositories.WarpConfigRepository;
import fr.pacifista.api.server.warps.service.repositories.WarpRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class WarpConfigCrudService extends ApiService<WarpConfigDTO, WarpConfig, WarpConfigMapper, WarpConfigRepository> {

    private final WarpRepository warpRepository;
    private final WarpCrudService warpCrudService;

    public WarpConfigCrudService(WarpConfigRepository repository,
                                 WarpConfigMapper mapper,
                                 WarpCrudService warpCrudService) {
        super(repository, mapper);
        this.warpRepository = warpCrudService.getRepository();
        this.warpCrudService = warpCrudService;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<WarpConfig> entity) {
        final Set<String> warpsIds = new HashSet<>();

        UUID warpId;
        for (final WarpConfig warpPortal : entity) {
            warpId = warpPortal.getWarp().getUuid();
            if (warpId == null) {
                throw new ApiBadRequestException("L'id du warp est requis");
            }

            warpsIds.add(warpId.toString());
        }

        final Iterable<Warp> warps = warpRepository.findAllByUuidIn(warpsIds);

        Warp warp;
        for (final WarpConfig warpConfig : entity) {
            warpId = warpConfig.getWarp().getUuid();
            if (warpId == null) {
                throw new ApiBadRequestException("L'id du warp est requis");
            }

            warp = warpCrudService.getEntityFromUidInList(warps, warpId);
            if (warp == null) {
                throw new ApiNotFoundException("Le warp avec l'id " + warpId + " n'existe pas");
            } else {
                warpConfig.setWarp(warp);
            }
        }
    }
}
