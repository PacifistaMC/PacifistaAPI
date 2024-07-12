package fr.pacifista.api.web.vote.service.services.external.com.minecraft.serveur;

import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import fr.pacifista.api.web.vote.service.services.external.ExternalVoteService;
import org.springframework.stereotype.Service;

@Service
public class ServeurMinecraftComVoteService extends ExternalVoteService {

    public ServeurMinecraftComVoteService() {
        super(VoteWebsite.SERVEUR_MINECRAFT_COM);
    }

    @Override
    public boolean hasVoted(String apiBodyResponse) {
        return apiBodyResponse.equals("1");
    }

}
