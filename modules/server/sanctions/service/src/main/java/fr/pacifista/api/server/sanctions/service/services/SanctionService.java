package fr.pacifista.api.server.sanctions.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.sanctions.client.dtos.SanctionDTO;
import fr.pacifista.api.server.sanctions.client.enums.SanctionType;
import fr.pacifista.api.server.sanctions.service.entities.Sanction;
import fr.pacifista.api.server.sanctions.service.mappers.SanctionMapper;
import fr.pacifista.api.server.sanctions.service.repositories.SanctionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class SanctionService extends ApiService<SanctionDTO, Sanction, SanctionMapper, SanctionRepository> {

    public SanctionService(SanctionRepository repository,
                           SanctionMapper mapper) {
        super(repository, mapper);
    }

    /**
     * Check if the player is sanctioned, if not returns server 404 code
     * @param playerUuid Mojang player UUID
     * @param sanctionType Sanction type BAN, MUTE, KICK, ...
     * @return SanctionDTO if active sanction
     */
    public SanctionDTO isPlayerSanctioned(final String playerUuid, final SanctionType sanctionType) throws ApiNotFoundException {
        final Optional<Sanction> search = getRepository()
                .findFirstByPlayerSanctionUuidAndActiveIsTrueAndSanctionTypeOrderByCreatedAtDesc(playerUuid, sanctionType);

        return getDTOWithSearch(search);
    }

    /**
     * Check if the ip is sanctioned, if not returns server 404 code
     * @param playerIp player ip
     * @param sanctionType Sanction type BAN, MUTE, KICK, ...
     * @return SanctionDTO if active sanction
     */
    public SanctionDTO isIpSanctioned(final String playerIp, final SanctionType sanctionType) throws ApiNotFoundException {
        final Optional<Sanction> search = getRepository()
                .findFirstByPlayerSanctionIpAndActiveIsTrueAndSanctionTypeOrderByCreatedAtDesc(playerIp, sanctionType);

        return getDTOWithSearch(search);
    }

    private SanctionDTO getDTOWithSearch(final Optional<Sanction> search) throws ApiNotFoundException {
        if (search.isPresent()) {
            final Sanction sanction = search.get();

            if (sanction.getExpirationDate() == null ||
                    sanction.getExpirationDate().toInstant().isAfter(Instant.now())) {
                return getMapper().toDto(sanction);
            } else {
                throw new ApiNotFoundException("Il n'y a pas de sanction en cours.");
            }
        } else {
            throw new ApiNotFoundException("Il n'y a pas de sanction en cours.");
        }
    }

}
