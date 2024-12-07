package fr.pacifista.api.web.vote.service.services;

import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import com.google.common.base.Strings;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import fr.pacifista.api.web.vote.client.clients.VoteClient;
import fr.pacifista.api.web.vote.client.dtos.VoteDTO;
import fr.pacifista.api.web.vote.client.dtos.VoteWebsiteDTO;
import fr.pacifista.api.web.vote.client.dtos.VotesCountDTO;
import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "VoteService")
@Service
@RequiredArgsConstructor
public class VoteService implements VoteClient {

    private static final String MAX_ELEMS_PER_PAGE = "50";

    private final VoteCrudService crudService;
    private final TopVotesService topVotesService;

    @Override
    public PageDTO<VoteDTO> getAll(String page, @Nullable String username, @Nullable String month, @Nullable String year) {
        return this.crudService.getAll(
                Strings.isNullOrEmpty(page) ? "0" : page,
                MAX_ELEMS_PER_PAGE,
                buildGetAllQuery(username, month, year),
                "createdAt:desc"
        );
    }

    @Override
    public List<VotesCountDTO> getTop(String month, String year) {
        return this.topVotesService.getTopVotes(
                parseInt(month, "Le mois"),
                parseInt(year, "L'année")
        );
    }

    @Override
    public List<VoteDTO> checkVotes(String[] usernames) {
        final List<VoteDTO> votes = new ArrayList<>();
        final String query = String.format(
                "username:%s:[%s],voteValidationDate:%s:%s,nextVoteDate:%s:true",
                SearchOperation.EQUALS_IGNORE_CASE.getOperation(),
                String.join("|", usernames),
                SearchOperation.GREATER_THAN_OR_EQUAL_TO.getOperation(),
                Instant.now().minus(1, ChronoUnit.DAYS),
                SearchOperation.IS_NOT_NULL.getOperation()
        );

        PageDTO<VoteDTO> page = new PageDTO<>(new ArrayList<>(), 2, 0, 10L, 10);
        while (page.getActualPage() < page.getTotalPages()) {
            page = this.crudService.getAll(
                    String.valueOf(page.getActualPage()),
                    "300",
                    query,
                    "createdAt:desc"
            );
            page.setActualPage(page.getActualPage() + 1);

            for (final VoteDTO vote : page.getContent()) {
                if (!this.isVoteListHasWebsite(votes, vote)) {
                    votes.add(vote);
                }
            }
        }

        return votes;
    }

    @Override
    public VoteDTO checkVote(String voteWebsite) {
        final VoteDTO vote = this.crudService.getVoteByUserAndWebsite(parseVoteWebsiteType(voteWebsite));

        if (vote == null) {
            throw new ApiNotFoundException("Vous n'avez pas encore voté.");
        } else {
            return vote;
        }
    }

    @Override
    @Transactional
    public VoteDTO vote(String voteWebsite) {
        final PacifistaWebUserLinkDTO user = this.crudService.getCurrentLinkedUser();
        final VoteWebsite website = parseVoteWebsiteType(voteWebsite);
        final VoteDTO vote = this.crudService.getVoteByUserAndWebsite(website);

        if (vote == null || vote.getVoteValidationDate() != null) {
            return this.crudService.create(
                    new VoteDTO(
                            user.getMinecraftUsername().toLowerCase(),
                            website,
                            null,
                            null
                    )
            );
        } else {
            return this.crudService.update(vote);
        }
    }

    @Override
    public List<VoteWebsiteDTO> getAvailableVoteWebsites() {
        final List<VoteWebsiteDTO> websites = new ArrayList<>();

        for (VoteWebsite website : VoteWebsite.values()) {
            if (website.isEnabled()) {
                websites.add(website.toDTO());
            }
        }

        return websites;
    }

    private static VoteWebsite parseVoteWebsiteType(String voteWebsite) {
        try {
            return VoteWebsite.valueOf(voteWebsite);
        } catch (Exception e) {
            throw new ApiBadRequestException("Le site de vote entré n'est pas valide.");
        }
    }

    private static int parseInt(String data, @NonNull String inputName) {
        if (Strings.isNullOrEmpty(data)) {
            throw new ApiBadRequestException(String.format("%s est obligatoire.", inputName));
        }

        try {
            return Integer.parseInt(data);
        } catch (NumberFormatException e) {
            throw new ApiBadRequestException(String.format("La nombre entré pour %s n'est pas un nombre valide", inputName.toLowerCase()));
        }
    }

    @NonNull
    private static String buildGetAllQuery(@Nullable String username, @Nullable String month, @Nullable String year) {
        final List<String> queryBuilder = new ArrayList<>();

        if (!Strings.isNullOrEmpty(username)) {
            queryBuilder.add(
                    String.format(
                            "username:%s:%s",
                            SearchOperation.EQUALS_IGNORE_CASE.getOperation(),
                            username
                    )
            );
        }

        if (!Strings.isNullOrEmpty(month)) {
            queryBuilder.add(
                    String.format(
                            "month:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            month
                    )
            );
        }

        if (!Strings.isNullOrEmpty(year)) {
            queryBuilder.add(
                    String.format(
                            "year:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            year
                    )
            );
        }

        return String.join(",", queryBuilder);
    }

    private boolean isVoteListHasWebsite(final List<VoteDTO> votes, final VoteDTO vote) {
        for (final VoteDTO voteInList : votes) {
            if (voteInList.getUsername().equalsIgnoreCase(vote.getUsername()) &&
                    voteInList.getVoteWebsite().equals(vote.getVoteWebsite())) {
                return true;
            }
        }

        return false;
    }

}
