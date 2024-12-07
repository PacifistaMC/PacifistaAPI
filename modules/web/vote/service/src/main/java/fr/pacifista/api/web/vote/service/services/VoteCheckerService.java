package fr.pacifista.api.web.vote.service.services;

import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class VoteCheckerService {

    public boolean hasVoted(@NonNull VoteWebsite voteWebsite, @NonNull Instant createdAt) {
        return voteWebsite.isEnabled() && createdAt.plus(10, ChronoUnit.SECONDS).isBefore(Instant.now());
    }

}
