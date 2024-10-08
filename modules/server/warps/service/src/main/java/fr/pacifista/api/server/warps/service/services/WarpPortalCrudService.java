package fr.pacifista.api.server.warps.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.warps.client.dtos.WarpPortalDTO;
import fr.pacifista.api.server.warps.service.entities.WarpPortal;
import fr.pacifista.api.server.warps.service.mappers.WarpPortalMapper;
import fr.pacifista.api.server.warps.service.repositories.WarpPortalRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class WarpPortalCrudService extends ApiService<WarpPortalDTO, WarpPortal, WarpPortalMapper, WarpPortalRepository> {

    private final WarpCrudService warpCrudService;

    public WarpPortalCrudService(WarpPortalRepository repository,
                                 WarpPortalMapper mapper,
                                 WarpCrudService warpCrudService) {
        super(repository, mapper);
        this.warpCrudService = warpCrudService;
    }

    @Override
    public void afterMapperCall(@NonNull WarpPortalDTO dto, @NonNull WarpPortal entity) {
        entity.setWarp(warpCrudService.getWarp(dto.getWarp().getId()));
    }

}
