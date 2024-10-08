package fr.pacifista.api.server.warps.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.warps.client.dtos.WarpConfigDTO;
import fr.pacifista.api.server.warps.service.entities.WarpConfig;
import fr.pacifista.api.server.warps.service.mappers.WarpConfigMapper;
import fr.pacifista.api.server.warps.service.repositories.WarpConfigRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class WarpConfigCrudService extends ApiService<WarpConfigDTO, WarpConfig, WarpConfigMapper, WarpConfigRepository> {

    private final WarpCrudService warpCrudService;

    public WarpConfigCrudService(WarpConfigRepository repository,
                                 WarpConfigMapper mapper,
                                 WarpCrudService warpCrudService) {
        super(repository, mapper);
        this.warpCrudService = warpCrudService;
    }

    @Override
    public void afterMapperCall(@NonNull WarpConfigDTO dto, @NonNull WarpConfig entity) {
        entity.setWarp(warpCrudService.getWarp(dto.getWarp().getId()));
    }

}
