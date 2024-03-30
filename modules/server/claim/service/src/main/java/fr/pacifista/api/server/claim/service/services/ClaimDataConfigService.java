package fr.pacifista.api.server.claim.service.services;

import com.funixproductions.core.crud.services.ApiService;
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
        if (dto.getId() != null) {
            entity.setClaim(null);
        } else {
            assignClaim(dto, entity);
        }
    }

    protected ClaimDataConfig generateNewConfig(final ClaimData newClaim) {
        final ClaimDataConfig config = new ClaimDataConfig();

        config.setExplosionEnabled(false);
        config.setFireSpreadEnabled(false);
        config.setMobGriefingEnabled(false);
        config.setPvpEnabled(false);
        config.setPublicAccess(true);
        config.setPublicInteractButtons(true);
        config.setPublicInteractDoorsTrapDoors(true);
        config.setPublicInteractChests(false);
        config.setAnimalProtection(true);
        config.setGriefProtection(true);

        config.setClaim(newClaim);
        return this.getRepository().save(config);
    }

    private void assignClaim(@NonNull ClaimDataConfigDTO dto,
                             @NonNull ClaimDataConfig entity) {
        if (dto.getClaimDataId() != null) {
            final ClaimData claim = claimDataRepository.findByUuid(dto.getClaimDataId().toString())
                    .orElseThrow(() -> new ApiNotFoundException("Claim parent non trouvé"));
            entity.setClaim(claim);
        } else {
            entity.setClaim(null);
        }
    }

}
