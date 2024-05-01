package fr.pacifista.api.web.user.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.web.user.service.entities.PacifistaWebUserLink;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacifistaWebUserLinkRepository extends ApiRepository<PacifistaWebUserLink> {

    Optional<PacifistaWebUserLink> findByFunixProdUserId(String funixProdUserId);
    Optional<PacifistaWebUserLink> findByMinecraftUuid(String minecraftUuid);

}
