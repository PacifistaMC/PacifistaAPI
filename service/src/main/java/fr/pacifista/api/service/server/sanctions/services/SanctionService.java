package fr.pacifista.api.service.server.sanctions.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.sanctions.dtos.SanctionDTO;
import fr.pacifista.api.client.sanctions.enums.SanctionType;
import fr.pacifista.api.service.server.sanctions.entities.Sanction;
import fr.pacifista.api.service.server.sanctions.mappers.SanctionMapper;
import fr.pacifista.api.service.server.sanctions.repositories.SanctionRepository;
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
