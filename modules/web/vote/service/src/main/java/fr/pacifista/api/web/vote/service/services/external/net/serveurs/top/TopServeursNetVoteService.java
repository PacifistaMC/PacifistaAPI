package fr.pacifista.api.web.vote.service.services.external.net.serveurs.top;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import fr.pacifista.api.web.vote.service.services.external.ExternalVoteService;
import org.springframework.stereotype.Service;

@Service
public class TopServeursNetVoteService extends ExternalVoteService {

    public TopServeursNetVoteService() {
        super(VoteWebsite.TOP_SERVEURS_NET);
    }

    @Override
    protected boolean hasVoted(String apiBodyResponse) {
        try {
            final JsonElement parser = JsonParser.parseString(apiBodyResponse);
            return parser.getAsJsonObject().get("success").getAsBoolean();
        } catch (Exception e) {
            return false;
        }
    }
}
