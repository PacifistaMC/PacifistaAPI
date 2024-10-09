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
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WarpConfigCrudService extends ApiService<WarpConfigDTO, WarpConfig, WarpConfigMapper, WarpConfigRepository> {

    private final WarpRepository warpRepository;

    public WarpConfigCrudService(WarpConfigRepository repository,
                                 WarpConfigMapper mapper,
                                 WarpRepository warpRepository) {
        super(repository, mapper);
        this.warpRepository = warpRepository;
    }

    @Override
    public void afterMapperCall(@NonNull WarpConfigDTO dto, @NonNull WarpConfig entity) {
        entity.setWarp(this.getWarp(dto.getWarp().getId()));
    }

    @NotNull
    protected Warp getWarp(UUID warpId) {
        if (warpId == null) {
            throw new ApiBadRequestException("L'id du warp est requis");
        }

        final Warp warp = this.warpRepository.findByUuid(warpId.toString()).orElse(null);
        if (warp == null) {
            throw new ApiNotFoundException("Le warp avec l'id " + warpId + " n'existe pas");
        }
        return warp;
    }

}
