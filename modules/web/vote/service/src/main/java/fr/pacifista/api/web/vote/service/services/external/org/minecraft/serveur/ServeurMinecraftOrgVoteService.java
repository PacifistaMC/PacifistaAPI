package fr.pacifista.api.web.vote.service.services.external.org.minecraft.serveur;

import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import fr.pacifista.api.web.vote.service.services.external.ExternalVoteService;
import org.springframework.stereotype.Service;

@Service
public class ServeurMinecraftOrgVoteService extends ExternalVoteService {

    public ServeurMinecraftOrgVoteService() {
        super(VoteWebsite.SERVEUR_MINECRAFT_ORG);
    }

    @Override
    public boolean hasVoted(String apiBodyResponse) {
        return apiBodyResponse.equals("1");
    }

}
