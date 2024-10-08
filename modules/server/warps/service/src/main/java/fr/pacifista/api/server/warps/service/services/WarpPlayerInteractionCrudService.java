package fr.pacifista.api.server.warps.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.warps.client.dtos.WarpPlayerInteractionDTO;
import fr.pacifista.api.server.warps.service.entities.WarpPlayerInteraction;
import fr.pacifista.api.server.warps.service.mappers.WarpPlayerInteractionMapper;
import fr.pacifista.api.server.warps.service.repositories.WarpPlayerInteractionRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

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

}
