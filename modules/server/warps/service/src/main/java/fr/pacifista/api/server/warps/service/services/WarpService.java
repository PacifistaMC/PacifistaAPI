package fr.pacifista.api.server.warps.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import fr.pacifista.api.server.warps.service.entities.Warp;
import fr.pacifista.api.server.warps.service.mappers.WarpMapper;
import fr.pacifista.api.server.warps.service.repositories.WarpRepository;
import org.springframework.stereotype.Service;

@Service
public class WarpService extends ApiService<WarpDTO, Warp, WarpMapper, WarpRepository> {
    public WarpService(WarpRepository repository,
                       WarpMapper mapper) {
        super(repository, mapper);
    }
}
