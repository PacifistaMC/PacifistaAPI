package fr.pacifista.api.web.vote.service.services;

import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import fr.pacifista.api.web.vote.service.services.external.ExternalVoteService;
import fr.pacifista.api.web.vote.service.services.external.com.minecraft.serveur.ServeurMinecraftComVoteService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VoteCheckerService {

    private final Map<VoteWebsite, ExternalVoteService> externalVoteServices;

    public VoteCheckerService(ServeurMinecraftComVoteService serveurMinecraftComVoteService,
                              ServeurMinecraftComVoteService serveurMinecraftOrgVoteService,
                              ServeurMinecraftComVoteService serveurPriveNetVoteService) {
        this.externalVoteServices = Map.of(
                //VoteWebsite.SERVEUR_MINECRAFT_COM, serveurMinecraftComVoteService,
                VoteWebsite.SERVEUR_MINECRAFT_ORG, serveurMinecraftOrgVoteService,
                VoteWebsite.SERVEUR_PRIVE_NET, serveurPriveNetVoteService
        );
    }

    public boolean hasVoted(@NonNull VoteWebsite voteWebsite, @NonNull String userIp) {
        return this.externalVoteServices.get(voteWebsite).checkVote(userIp);
    }

}
