package fr.pacifista.api.server.claim.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataConfigDTO;
import fr.pacifista.api.server.claim.service.entities.ClaimData;
import fr.pacifista.api.server.claim.service.entities.ClaimDataConfig;
import fr.pacifista.api.server.claim.service.mappers.ClaimDataConfigMapper;
import fr.pacifista.api.server.claim.service.repositories.ClaimDataConfigRepository;
import fr.pacifista.api.server.claim.service.repositories.ClaimDataRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ClaimDataConfigService extends ApiService<ClaimDataConfigDTO, ClaimDataConfig, ClaimDataConfigMapper, ClaimDataConfigRepository> {

    private final ClaimDataRepository claimDataRepository;

    public ClaimDataConfigService(ClaimDataConfigRepository repository,
                                  ClaimDataConfigMapper mapper,
                                  ClaimDataRepository claimDataRepository) {
        super(repository, mapper);
        this.claimDataRepository = claimDataRepository;
    }

    @Override
    public void afterMapperCall(@NonNull ClaimDataConfigDTO dto,
                                @NonNull ClaimDataConfig entity) {
        assignClaim(dto, entity);
    }

    private void assignClaim(@NonNull ClaimDataConfigDTO dto,
                             @NonNull ClaimDataConfig entity) {
        if (dto.getClaimDataId() != null) {
            final ClaimData claim = claimDataRepository.findByUuid(dto.getClaimDataId())
                    .orElseThrow(() -> new ApiNotFoundException("Claim parent non trouvé"));
            entity.setClaim(claim);
        } else {
            throw new ApiBadRequestException("Claim parent non trouvé. Id manquant.");
        }
    }

}
