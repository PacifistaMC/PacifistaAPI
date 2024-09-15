package fr.pacifista.api.web.vote.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import fr.pacifista.api.web.vote.service.entities.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface VoteRepository extends ApiRepository<Vote> {

    Optional<Vote> findFirstByUsernameAndVoteWebsiteOrderByCreatedAtDesc(String username, VoteWebsite voteWebsite);

    @Query("SELECT v FROM pacifista_web_votes v WHERE v.voteValidationDate IS NULL AND COALESCE(v.updatedAt, v.createdAt) >= :cutoffDate")
    Iterable<Vote> findAllByVoteValidationDateIsNullAndCreatedAtAfter(@Param("cutoffDate") LocalDateTime cutoffDate);

    Iterable<Vote> findAllByVoteValidationDateIsNotNullAndMonthVoteAndYearVote(int month, int year);
}
