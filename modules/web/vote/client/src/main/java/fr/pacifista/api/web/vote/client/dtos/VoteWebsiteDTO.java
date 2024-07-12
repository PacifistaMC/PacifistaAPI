package fr.pacifista.api.web.vote.client.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteWebsiteDTO {
    private String enumName;
    private String displayName;
    private String urlVote;
    private int coolDownInMinutes;
}
