package fr.pacifista.api.web.vote.client.dtos;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VotesCountDTO {

    @NonNull
    private final String username;

    private int votesCount = 1;

    public boolean equalsUsername(String username) {
        return this.username.equalsIgnoreCase(username);
    }

    public void incrementVotesCount() {
        ++this.votesCount;
    }

}
