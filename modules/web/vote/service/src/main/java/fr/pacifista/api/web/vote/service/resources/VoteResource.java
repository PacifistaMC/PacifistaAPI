package fr.pacifista.api.web.vote.service.resources;

import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.web.vote.client.clients.VoteClient;
import fr.pacifista.api.web.vote.client.clients.VoteClientImpl;
import fr.pacifista.api.web.vote.client.dtos.VoteDTO;
import fr.pacifista.api.web.vote.client.dtos.VoteWebsiteDTO;
import fr.pacifista.api.web.vote.client.dtos.VotesCountDTO;
import fr.pacifista.api.web.vote.service.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/" + VoteClientImpl.PATH)
@RequiredArgsConstructor
public class VoteResource implements VoteClient {

    private final VoteService service;

    @Override
    public PageDTO<VoteDTO> getAll(String page, String username, String month, String year) {
        return service.getAll(page, username, month, year);
    }

    @Override
    public List<VotesCountDTO> getTop(String month, String year) {
        return service.getTop(month, year);
    }

    @Override
    public List<VoteDTO> checkVotes(String[] usernames) {
        return service.checkVotes(usernames);
    }

    @Override
    public List<VoteWebsiteDTO> getAvailableVoteWebsites() {
        return service.getAvailableVoteWebsites();
    }

    @Override
    public VoteDTO checkVote(String voteWebsite) {
        return service.checkVote(voteWebsite);
    }

    @Override
    public VoteDTO vote(String voteWebsite) {
        return service.vote(voteWebsite);
    }
}
