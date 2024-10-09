package fr.pacifista.api.server.warps.service.services;

import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.warps.client.dtos.WarpConfigDTO;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import fr.pacifista.api.server.warps.service.entities.Warp;
import fr.pacifista.api.server.warps.service.mappers.WarpMapper;
import fr.pacifista.api.server.warps.service.repositories.WarpPlayerInteractionRepository;
import fr.pacifista.api.server.warps.service.repositories.WarpPortalRepository;
import fr.pacifista.api.server.warps.service.repositories.WarpRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WarpCrudService extends ApiService<WarpDTO, Warp, WarpMapper, WarpRepository> {

    private final WarpConfigCrudService warpConfigCrudService;
    private final WarpPlayerInteractionRepository warpPlayerInteractionRepository;
    private final WarpPortalRepository warpPortalRepository;

    public WarpCrudService(WarpRepository repository,
                           WarpMapper mapper,
                           WarpConfigCrudService warpConfigCrudService,
                           WarpPlayerInteractionRepository warpPlayerInteractionRepository,
                           WarpPortalRepository warpPortalRepository) {
        super(repository, mapper);
        this.warpConfigCrudService = warpConfigCrudService;
        this.warpPlayerInteractionRepository = warpPlayerInteractionRepository;
        this.warpPortalRepository = warpPortalRepository;
    }

    @Override
    @Transactional
    public List<WarpDTO> create(List<@Valid WarpDTO> request) {
        final List<WarpDTO> res = super.create(request);

        for (WarpDTO warpDTO : res) {
            if (warpDTO.getConfig() == null) {
                warpDTO.setConfig(this.warpConfigCrudService.create(WarpConfigDTO.initWithDefaults(warpDTO)));
            }
        }

        return res;
    }

    @Override
    @Transactional
    public List<WarpDTO> updatePut(List<@Valid WarpDTO> request) {
        final List<WarpConfigDTO> warpConfigDTOs = this.getDTOSInWarpsIds(request.stream().map(WarpDTO::getId).toList());
        final Map<UUID, UUID> warpsConfigsIds = new HashMap<>();

        for (final WarpDTO warpDTO : request) {
            warpsConfigsIds.put(warpDTO.getId(), warpDTO.getConfig().getId());
            warpDTO.setConfig(null);
        }

        final List<WarpDTO> res = super.updatePut(request);

        UUID configId;
        for (final WarpDTO warpDTO : res) {
            configId = warpsConfigsIds.get(warpDTO.getId());
            if (configId != null) {
                warpDTO.setConfig(
                        this.warpConfigCrudService.getDTOFromIdInList(warpConfigDTOs, configId)
                );
            }
        }
        return res;
    }

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<Warp> entity) {
        this.warpPortalRepository.deleteAllByWarpIn(entity);
        this.warpPlayerInteractionRepository.deleteAllByWarpIn(entity);
        this.warpConfigCrudService.getRepository().deleteAllByWarpIn(entity);
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

    private List<WarpConfigDTO> getDTOSInWarpsIds(final List<UUID> warpsIds) {
        final List<WarpConfigDTO> dtos = new ArrayList<>();
        final String searchQuery = String.format(
                "warp.uuid:%s:[%s]",
                SearchOperation.EQUALS.getOperation(),
                String.join("|", warpsIds.stream().map(UUID::toString).toArray(String[]::new))
        );

        PageDTO<WarpConfigDTO> page = new PageDTO<>(new ArrayList<>(), 2, 0, 10L, 10);

        while (page.getActualPage() < page.getTotalPages()) {
            page = this.warpConfigCrudService.getAll(Integer.toString(page.getActualPage()), "300", searchQuery, "");
            dtos.addAll(page.getContent());
            page.setActualPage(page.getActualPage() + 1);
        }

        return dtos;
    }

}
