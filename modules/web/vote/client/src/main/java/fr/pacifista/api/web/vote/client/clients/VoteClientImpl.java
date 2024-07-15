package fr.pacifista.api.web.vote.client.clients;

import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.web.vote.client.dtos.VoteDTO;
import fr.pacifista.api.web.vote.client.dtos.VoteWebsiteDTO;
import fr.pacifista.api.web.vote.client.dtos.VotesCountDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.lang.Nullable;

import java.util.List;

public class VoteClientImpl implements VoteClient {

    public static final String PATH = "web/vote";

    private final VoteClient client;

    public VoteClientImpl() {
        final String pacifistaApiDomain = System.getenv("PACIFISTA_API_URL_DOMAIN");
        final Gson gson = new GsonBuilder().setDateFormat(FeignImpl.DATE_FORMAT).create();

        this.client = Feign.builder()
                .requestInterceptor(new FeignTokenInterceptor())
                .contract(new SpringMvcContract())
                .client(new OkHttpClient())
                .decoder(new GsonDecoder(gson))
                .encoder(new GsonEncoder(gson))
                .target(
                        VoteClient.class, String.format("%s/%s",
                                Strings.isBlank(pacifistaApiDomain) ? "https://api.pacifista.fr" : pacifistaApiDomain,
                                PATH)
                );
    }

    @Override
    public PageDTO<VoteDTO> getAll(String page, @Nullable String username, @Nullable String month, @Nullable String year) {
        try {
            return this.client.getAll(page, username, month, year);
        } catch (FeignException e) {
            throw FeignImpl.handleFeignException(e);
        }
    }

    @Override
    public List<VoteDTO> checkVotes(String[] usernames) {
        try {
            return this.client.checkVotes(usernames);
        } catch (FeignException e) {
            throw FeignImpl.handleFeignException(e);
        }
    }

    @Override
    public List<VotesCountDTO> getTop(String month, String year) {
        try {
            return this.client.getTop( month, year);
        } catch (FeignException e) {
            throw FeignImpl.handleFeignException(e);
        }
    }

    @Override
    public List<VoteWebsiteDTO> getAvailableVoteWebsites() {
        return List.of();
    }

    @Override
    public VoteDTO checkVote(String voteWebsite) {
        throw new ApiBadRequestException("Fonctionalité disponible sur pacifista.fr/vote");
    }

    @Override
    public VoteDTO vote(String voteWebsite, String username) {
        throw new ApiBadRequestException("Fonctionalité disponible sur pacifista.fr/vote");
    }
}
