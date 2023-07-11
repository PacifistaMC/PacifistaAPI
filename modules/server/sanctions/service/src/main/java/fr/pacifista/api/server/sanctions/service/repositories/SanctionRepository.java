package fr.pacifista.api.server.sanctions.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.sanctions.client.enums.SanctionType;
import fr.pacifista.api.server.sanctions.service.entities.Sanction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SanctionRepository extends ApiRepository<Sanction> {

    Optional<Sanction> findFirstByPlayerSanctionUuidAndActiveIsTrueAndSanctionTypeOrderByCreatedAtDesc(String playerUuid, SanctionType sanctionType);
    Optional<Sanction> findFirstByPlayerSanctionIpAndActiveIsTrueAndSanctionTypeOrderByCreatedAtDesc(String playerIp, SanctionType sanctionType);

}
