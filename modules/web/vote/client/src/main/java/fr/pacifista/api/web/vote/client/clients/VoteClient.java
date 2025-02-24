package fr.pacifista.api.web.vote.client.clients;

import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.web.vote.client.dtos.VoteDTO;
import fr.pacifista.api.web.vote.client.dtos.VoteWebsiteDTO;
import fr.pacifista.api.web.vote.client.dtos.VotesCountDTO;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface VoteClient {

    /**
     * Utilisé pour récupérér les votes individuelement pour avoir l'historique des votes
     * @param page la page à récupérer
     * @param username le nom de l'utilisateur, si null ou vide récupère tous les votes
     * @param month le mois à récupérer, si null ou vide récupère tous les votes
     * @param year l'année à récupérer, si null ou vide récupère tous les votes
     * @return page de votes
     */
    @GetMapping
    PageDTO<VoteDTO> getAll(
            @RequestParam(value = "page", defaultValue = "0") String page,
            @RequestParam(value = "username", required = false) @Nullable String username,
            @RequestParam(value = "month", required = false) @Nullable String month,
            @RequestParam(value = "year", required = false) @Nullable String year
    );

    /**
     * Utilisé pour récupérer les votes pour le top classement (top 50)
     * @param month le mois, non null (1-12)
     * @param year l'année, non null (yyyy)
     * @return liste de votes trié par nombre de votes
     */
    @GetMapping("top")
    List<VotesCountDTO> getTop(
            @RequestParam(value = "month") String month,
            @RequestParam(value = "year") String year
    );

    @GetMapping("websites")
    List<VoteWebsiteDTO> getAvailableVoteWebsites();

    /**
     * Utilisé pour vérifier si un utilisateur a déjà voté sur un site de vote
     * @param voteWebsite le site de vote
     * @return le vote si l'utilisateur a déjà voté, 404 si pas de vote
     */
    @GetMapping("user/{voteWebsite}")
    VoteDTO checkVote(@PathVariable("voteWebsite") String voteWebsite);

    /**
     * Utilisé pour voter sur un site de vote
     * @param voteWebsite le site de vote
     * @return le vote
     */
    @PostMapping("user/{voteWebsite}")
    VoteDTO vote(@PathVariable("voteWebsite") String voteWebsite);

}
