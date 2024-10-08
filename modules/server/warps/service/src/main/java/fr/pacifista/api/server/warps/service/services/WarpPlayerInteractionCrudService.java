package fr.pacifista.api.server.warps.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.warps.client.dtos.WarpPlayerInteractionDTO;
import fr.pacifista.api.server.warps.service.entities.WarpPlayerInteraction;
import fr.pacifista.api.server.warps.service.mappers.WarpPlayerInteractionMapper;
import fr.pacifista.api.server.warps.service.repositories.WarpPlayerInteractionRepository;
import org.springframework.stereotype.Service;

@Service
public class WarpPlayerInteractionCrudService extends ApiService<WarpPlayerInteractionDTO, WarpPlayerInteraction, WarpPlayerInteractionMapper, WarpPlayerInteractionRepository> {
    public WarpPlayerInteractionCrudService(WarpPlayerInteractionRepository repository,
                                           WarpPlayerInteractionMapper mapper) {
        super(repository, mapper);
    }
}
