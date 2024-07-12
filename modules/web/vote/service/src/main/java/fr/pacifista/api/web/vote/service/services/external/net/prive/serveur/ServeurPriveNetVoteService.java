package fr.pacifista.api.web.vote.service.services.external.net.prive.serveur;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import fr.pacifista.api.web.vote.service.services.external.ExternalVoteService;
import org.springframework.stereotype.Service;

@Service
public class ServeurPriveNetVoteService extends ExternalVoteService {

    public ServeurPriveNetVoteService() {
        super(VoteWebsite.SERVEUR_PRIVE_NET);
    }

    @Override
    public boolean hasVoted(String apiBodyResponse) {
        try {
            final JsonElement parser = JsonParser.parseString(apiBodyResponse);
            return parser.getAsJsonObject().get("success").getAsBoolean();
        } catch (Exception e) {
            return false;
        }
    }
}
