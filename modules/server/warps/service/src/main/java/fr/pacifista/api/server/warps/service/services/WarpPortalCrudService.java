package fr.pacifista.api.server.warps.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.warps.client.dtos.WarpPortalDTO;
import fr.pacifista.api.server.warps.service.entities.Warp;
import fr.pacifista.api.server.warps.service.entities.WarpPortal;
import fr.pacifista.api.server.warps.service.mappers.WarpPortalMapper;
import fr.pacifista.api.server.warps.service.repositories.WarpPortalRepository;
import fr.pacifista.api.server.warps.service.repositories.WarpRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class WarpPortalCrudService extends ApiService<WarpPortalDTO, WarpPortal, WarpPortalMapper, WarpPortalRepository> {

    private final WarpRepository warpRepository;
    private final WarpCrudService warpCrudService;

    public WarpPortalCrudService(WarpPortalRepository repository,
                                 WarpPortalMapper mapper,
                                 WarpCrudService warpCrudService) {
        super(repository, mapper);
        this.warpRepository = warpCrudService.getRepository();
        this.warpCrudService = warpCrudService;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<WarpPortal> entity) {
        final Set<String> warpsIds = new HashSet<>();

        UUID warpId;
        for (final WarpPortal warpPortal : entity) {
            warpId = warpPortal.getWarp().getUuid();
            if (warpId == null) {
                throw new ApiBadRequestException("L'id du warp est requis");
            }

            warpsIds.add(warpId.toString());
        }

        final Iterable<Warp> warps = warpRepository.findAllByUuidIn(warpsIds);

        Warp warp;
        for (final WarpPortal warpPortal : entity) {
            warpId = warpPortal.getWarp().getUuid();
            if (warpId == null) {
                throw new ApiBadRequestException("L'id du warp est requis");
            }

            warp = warpCrudService.getEntityFromUidInList(warps, warpId);
            if (warp == null) {
                throw new ApiNotFoundException("Le warp avec l'id " + warpId + " n'existe pas");
            } else {
                warpPortal.setWarp(warp);
            }
        }
    }
}
