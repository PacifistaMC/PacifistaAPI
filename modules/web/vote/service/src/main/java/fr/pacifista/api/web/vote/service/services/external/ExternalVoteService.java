package fr.pacifista.api.web.vote.service.services.external;

import com.funixproductions.core.exceptions.ApiException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "ExternalVoteService")
@RequiredArgsConstructor
public abstract class ExternalVoteService {

    private final VoteWebsite voteWebsite;
    private final Cache<String, Boolean> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();
    private static final HttpClient client = HttpClient.newHttpClient();

    protected abstract boolean hasVoted(String apiBodyResponse);

    public final boolean checkVote(String userIp) {
        Boolean hasVoted = this.cache.getIfPresent(userIp);

        if (hasVoted != null) {
            return hasVoted;
        } else {
            try {
                final String httpResponse = callEndpoint(userIp);
                hasVoted = this.hasVoted(httpResponse);

                this.cache.put(userIp, hasVoted);
                return hasVoted;
            } catch (Exception e) {
                log.error("Error while checking vote on website {}, (userIP: {}) : {}", voteWebsite.name(), userIp, e.getMessage(), e);
                return false;
            }
        }
    }

    protected final String callEndpoint(final String userIp) throws ApiException {
        final String uri = makeUriAndEncodeUrl(userIp);
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();

        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final int statusCode = response.statusCode();

            if (statusCode >= 200 && statusCode < 300) {
                return response.body();
            } else {
                throw new ApiException("Erreur lors de check vote sur l'api " + this.voteWebsite + " : HttpCode Error " + statusCode + ".");
            }
        } catch (IOException e) {
            throw new ApiException("Erreur IO lors de check vote sur l'api " + this.voteWebsite + " : " + e.getMessage() + ".", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException("Erreur ThreadCUT lors de check vote sur l'api " + this.voteWebsite + " : " + e.getMessage() + ".", e);
        }
    }

    private String makeUriAndEncodeUrl(final String userIp) {
        return this.voteWebsite.getApiUrl().replace("{playerIp}", userIp);
    }

}
