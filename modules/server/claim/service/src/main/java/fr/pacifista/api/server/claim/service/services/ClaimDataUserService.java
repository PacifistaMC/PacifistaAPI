package fr.pacifista.api.server.claim.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataUserDTO;
import fr.pacifista.api.server.claim.service.entities.ClaimData;
import fr.pacifista.api.server.claim.service.entities.ClaimDataUser;
import fr.pacifista.api.server.claim.service.mappers.ClaimDataUserMapper;
import fr.pacifista.api.server.claim.service.repositories.ClaimDataRepository;
import fr.pacifista.api.server.claim.service.repositories.ClaimDataUserRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ClaimDataUserService extends ApiService<ClaimDataUserDTO, ClaimDataUser, ClaimDataUserMapper, ClaimDataUserRepository> {

    private final ClaimDataRepository claimDataRepository;

    public ClaimDataUserService(ClaimDataUserRepository repository,
                                ClaimDataUserMapper mapper,
                                ClaimDataRepository claimDataRepository) {
        super(repository, mapper);
        this.claimDataRepository = claimDataRepository;
    }

    @Override
    public void afterMapperCall(@NonNull ClaimDataUserDTO dto,
                                @NonNull ClaimDataUser entity) {
        if (dto.getId() != null) {
            entity.setClaim(null);
        } else {
            assignClaim(dto, entity);
        }
    }

    private void assignClaim(@NonNull ClaimDataUserDTO dto,
                             @NonNull ClaimDataUser entity) {
        if (dto.getClaimDataId() != null) {
            final ClaimData claim = claimDataRepository.findByUuid(dto.getClaimDataId().toString())
                    .orElseThrow(() -> new ApiNotFoundException("Claim parent non trouvé"));
            entity.setClaim(claim);
        } else {
            entity.setClaim(null);
        }
    }
}
