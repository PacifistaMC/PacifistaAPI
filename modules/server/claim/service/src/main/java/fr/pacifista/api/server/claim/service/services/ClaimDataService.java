package fr.pacifista.api.server.claim.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataDTO;
import fr.pacifista.api.server.claim.service.entities.ClaimData;
import fr.pacifista.api.server.claim.service.mappers.ClaimDataMapper;
import fr.pacifista.api.server.claim.service.repositories.ClaimDataRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

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
    public void afterMapperCall(@NonNull ClaimDataDTO dto,
                                @NonNull ClaimData entity) {
        if (dto.getId() != null) {
            entity.setParent(null);
        } else {
            assignParent(dto, entity);
        }
    }

    @Override
    public void afterSavingEntity(@NonNull Iterable<ClaimData> entity) {
        for (ClaimData claim : entity) {
            claim.setConfig(configService.generateNewConfig(claim));
        }
        getRepository().saveAll(entity);
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
