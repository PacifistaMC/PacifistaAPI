package fr.pacifista.api.web.vote.service.services;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.pacifista.api.web.vote.client.dtos.VoteDTO;
import fr.pacifista.api.web.vote.client.dtos.VotesCountDTO;
import fr.pacifista.api.web.vote.service.entities.Vote;
import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TopVotesService {

    private final VoteCrudService crudService;

    private final Cache<Pair<Integer, Integer>, List<VotesCountDTO>> topVotesCache = CacheBuilder.newBuilder()
            .expireAfterWrite(20, TimeUnit.SECONDS)
            .build();

    protected List<VotesCountDTO> getTopVotes(int month, int year) {
        final Pair<Integer, Integer> key = new Pair<>(month, year);
        final List<VotesCountDTO> votes = this.topVotesCache.getIfPresent(key);

        if (votes != null) {
            return votes;
        } else {
            final List<VotesCountDTO> topVotes = fetchTopVotesFromDatabase(month, year);

            this.topVotesCache.put(key, topVotes);
            return topVotes;
        }
    }

    /**
     * Fetch top votes from database
     * @param month month
     * @param year year
     * @return 50 top votes
     */
    private List<VotesCountDTO> fetchTopVotesFromDatabase(int month, int year) {
        final List<VotesCountDTO> votes = new ArrayList<>();
        final Iterable<Vote> dbVotes = this.crudService.getRepository().findAllByVoteValidationDateIsNotNullAndMonthVoteAndYearVote(
                month,
                year
        );

        VoteDTO voteDTO;
        for (final Vote vote : dbVotes) {
            voteDTO = this.crudService.getMapper().toDto(vote);

            if (!addVoteToTopVotes(votes, voteDTO)) {
                votes.add(new VotesCountDTO(voteDTO.getUsername()));
            }
        }

        votes.sort(Comparator.comparingInt(VotesCountDTO::getVotesCount).reversed());
        return votes.size() > 50 ? votes.subList(0, 50) : votes;
    }

    private boolean addVoteToTopVotes(List<VotesCountDTO> votes, VoteDTO voteDTO) {
        for (final VotesCountDTO votesCountDTO : votes) {
            if (votesCountDTO.equalsUsername(voteDTO.getUsername())) {
                votesCountDTO.incrementVotesCount();
                return true;
            }
        }

        return false;
    }

}
