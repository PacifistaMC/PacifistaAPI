package fr.pacifista.api.web.vote.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.tools.network.IPUtils;
import fr.pacifista.api.web.vote.client.dtos.VoteDTO;
import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import fr.pacifista.api.web.vote.service.entities.Vote;
import fr.pacifista.api.web.vote.service.mappers.VoteMapper;
import fr.pacifista.api.web.vote.service.repositories.VoteRepository;
import jakarta.servlet.http.HttpServletRequest;
import kotlin.Pair;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class VoteCrudService extends ApiService<VoteDTO, Vote, VoteMapper, VoteRepository> {

    private final VoteCheckerService voteCheckerService;
    private final VoteValidatedSuccessService rewardService;

    private final HttpServletRequest servletRequest;
    private final IPUtils ipUtils;

    public VoteCrudService(VoteRepository repository,
                           VoteMapper mapper,
                           VoteCheckerService voteCheckerService,
                           VoteValidatedSuccessService rewardService,
                           HttpServletRequest servletRequest,
                           IPUtils ipUtils) {
        super(repository, mapper);
        this.voteCheckerService = voteCheckerService;
        this.rewardService = rewardService;
        this.servletRequest = servletRequest;
        this.ipUtils = ipUtils;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<Vote> entity) {
        final String ip = ipUtils.getClientIp(servletRequest);
        final Pair<Integer, Integer> monthAndYear = getActualMonthAndYear();

        for (final Vote vote : entity) {
            if (vote.getPlayerIp() == null) {
                vote.setPlayerIp(ip);
            }

            if (vote.getMonthVote() == null) {
                vote.setMonthVote(monthAndYear.getFirst());
            }

            if (vote.getYearVote() == null) {
                vote.setYearVote(monthAndYear.getSecond());
            }
        }
    }

    @Nullable
    protected VoteDTO getVoteByUserIpAndWebsite(String userIp, VoteWebsite voteWebsite) {
        final Optional<Vote> vote = this.getRepository().findFirstByPlayerIpAndVoteWebsiteOrderByCreatedAtDesc(userIp, voteWebsite);

        return vote.map(this.getMapper()::toDto).orElse(null);
    }

    @Scheduled(fixedRate = 10, initialDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void checkPendingVotes() {
        final List<Vote> successVotes = new ArrayList<>();

        this.fetchAllPendingVotes().forEach(vote -> {
            if (this.voteCheckerService.hasVoted(vote.getVoteWebsite(), vote.getPlayerIp())) {
                vote.setVoteValidationDate(new Date());
                vote.setNextVoteDate(vote.getVoteWebsite().getNextVoteDate());
                vote.setPlayerIp("hidden-user-done-vote");
                successVotes.add(vote);
            }
        });
        getRepository().saveAll(successVotes);

        this.rewardService.sendRewards(successVotes);
    }

    private Iterable<Vote> fetchAllPendingVotes() {
        return getRepository().findAllByVoteValidationDateIsNullAndCreatedAtAfter(
                LocalDateTime.now().minusDays(2)
        );
    }

    private Pair<Integer, Integer> getActualMonthAndYear() {
        final LocalDateTime now = LocalDateTime.now();
        return new Pair<>(now.getMonthValue(), now.getYear());
    }

}