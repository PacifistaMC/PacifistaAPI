package fr.pacifista.api.service.sanctions.repositories;

import fr.funixgaming.api.core.crud.repositories.ApiRepository;
import fr.pacifista.api.client.sanctions.enums.SanctionType;
import fr.pacifista.api.service.sanctions.entities.Sanction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SanctionRepository extends ApiRepository<Sanction> {

    Optional<Sanction> findFirstByPlayerSanctionUuidAndActiveIsTrueAndSanctionTypeOrderByCreatedAtDesc(String playerUuid, SanctionType sanctionType);
    Optional<Sanction> findFirstByPlayerSanctionIpAndActiveIsTrueAndSanctionTypeOrderByCreatedAtDesc(String playerIp, SanctionType sanctionType);

}
