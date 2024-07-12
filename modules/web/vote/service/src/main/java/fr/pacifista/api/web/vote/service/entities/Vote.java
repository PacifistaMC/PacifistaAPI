package fr.pacifista.api.web.vote.service.entities;

import com.funixproductions.api.encryption.client.utils.EncryptionString;
import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "pacifista_web_votes")
public class Vote extends ApiEntity {

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_website", nullable = false)
    private VoteWebsite voteWebsite;

    /**
     * Used to store the month of the vote. This field is used to filter votes by month.
     */
    @Column(name = "month_vote", nullable = false)
    private Integer monthVote;

    /**
     * Used to store the year of the vote. This field is used to filter votes by year.
     */
    @Column(name = "year_vote", nullable = false)
    private Integer yearVote;

    @Column(name = "vote_validation_date")
    private Date voteValidationDate;

    @Column(name = "next_vote_date")
    private Date nextVoteDate;

    /**
     * The IP address of the player who voted. This field is encrypted to protect user IP address. Not available in DTO. The ip is replaced after a vote is validated.
     */
    @Convert(converter = EncryptionString.class)
    @Column(name = "player_ip", nullable = false)
    private String playerIp;

}
