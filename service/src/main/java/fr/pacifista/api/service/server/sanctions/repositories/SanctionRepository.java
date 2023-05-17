package fr.pacifista.api.service.server.sanctions.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.client.server.sanctions.enums.SanctionType;
import fr.pacifista.api.service.server.sanctions.entities.Sanction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SanctionRepository extends ApiRepository<Sanction> {

    Optional<Sanction> findFirstByPlayerSanctionUuidAndActiveIsTrueAndSanctionTypeOrderByCreatedAtDesc(String playerUuid, SanctionType sanctionType);
    Optional<Sanction> findFirstByPlayerSanctionIpAndActiveIsTrueAndSanctionTypeOrderByCreatedAtDesc(String playerIp, SanctionType sanctionType);

}
