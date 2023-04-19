package fr.pacifista.api.service.server.warps.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.pacifista.api.client.warps.dtos.WarpDTO;
import fr.pacifista.api.service.server.warps.entities.Warp;
import fr.pacifista.api.service.server.warps.mappers.WarpMapper;
import fr.pacifista.api.service.server.warps.repositories.WarpRepository;
import org.springframework.stereotype.Service;

@Service
public class WarpService extends ApiService<WarpDTO, Warp, WarpMapper, WarpRepository> {
    public WarpService(WarpRepository repository,
                       WarpMapper mapper) {
        super(repository, mapper);
    }
}
