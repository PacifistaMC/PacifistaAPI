package fr.pacifista.api.web.vote.service.services;

import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import fr.pacifista.api.web.vote.service.services.external.ExternalVoteService;
import fr.pacifista.api.web.vote.service.services.external.com.minecraft.serveur.ServeurMinecraftComVoteService;
import fr.pacifista.api.web.vote.service.services.external.net.serveurs.top.TopServeursNetVoteService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VoteCheckerService {

    private final Map<VoteWebsite, ExternalVoteService> externalVoteServices;

    public VoteCheckerService(ServeurMinecraftComVoteService serveurMinecraftComVoteService,
                              ServeurMinecraftComVoteService serveurMinecraftOrgVoteService,
                              ServeurMinecraftComVoteService serveurPriveNetVoteService,
                              TopServeursNetVoteService topServeursNetVoteService) {
        this.externalVoteServices = Map.of(
                VoteWebsite.SERVEUR_MINECRAFT_COM, serveurMinecraftComVoteService,
                VoteWebsite.SERVEUR_MINECRAFT_ORG, serveurMinecraftOrgVoteService,
                VoteWebsite.SERVEUR_PRIVE_NET, serveurPriveNetVoteService,
                VoteWebsite.TOP_SERVEURS_NET, topServeursNetVoteService
        );
    }

    public boolean hasVoted(@NonNull VoteWebsite voteWebsite, @NonNull String userIp) {
        if (Boolean.FALSE.equals(voteWebsite.isEnabled())) return false;

        final ExternalVoteService externalVoteService = this.externalVoteServices.get(voteWebsite);

        if (externalVoteService != null) {
            return externalVoteService.checkVote(userIp);
        } else {
            return false;
        }
    }

}
