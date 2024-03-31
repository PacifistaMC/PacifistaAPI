package fr.pacifista.api.server.claim.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataConfigDTO;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataDTO;
import fr.pacifista.api.server.claim.service.entities.ClaimData;
import fr.pacifista.api.server.claim.service.mappers.ClaimDataMapper;
import fr.pacifista.api.server.claim.service.repositories.ClaimDataRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimDataService extends ApiService<ClaimDataDTO, ClaimData, ClaimDataMapper, ClaimDataRepository> {

    private final ClaimDataConfigService configService;

    public ClaimDataService(ClaimDataRepository repository,
                            ClaimDataMapper mapper,
                            ClaimDataConfigService configService) {
        super(repository, mapper);
        this.configService = configService;
    }

    @Override
    @Transactional
    public @NonNull ClaimDataDTO create(ClaimDataDTO request) {
        final ClaimDataDTO res = super.create(request);

        if (res.getConfig() == null) {
            res.setConfig(configService.create(new ClaimDataConfigDTO(res)));
        }
        return res;
    }

    @Override
    @Transactional
    public List<ClaimDataDTO> create(List<@Valid ClaimDataDTO> request) {
        final List<ClaimDataDTO> res = super.create(request);

        for (ClaimDataDTO dto : res) {
            if (dto.getConfig() == null) {
                dto.setConfig(configService.create(new ClaimDataConfigDTO(dto)));
            }
        }
        return res;
    }

    @Override
    public void afterMapperCall(@NonNull ClaimDataDTO dto,
                                @NonNull ClaimData entity) {
        if (dto.getId() != null) {
            entity.setParent(null);
        } else {
            assignParent(dto, entity);
        }
    }

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<ClaimData> entity) {
        super.getRepository().deleteAllByParentIn(entity);
    }

    private void assignParent(@NonNull ClaimDataDTO dto,
                              @NonNull ClaimData entity) {
        if (dto.getParent() != null && dto.getParent().getId() != null) {
            final ClaimData parent = super.getRepository().findByUuid(dto.getParent().getId().toString())
                    .orElseThrow(() -> new ApiNotFoundException("Claim parent non trouvé"));
            entity.setParent(parent);
        } else {
            entity.setParent(null);
        }
    }
}
