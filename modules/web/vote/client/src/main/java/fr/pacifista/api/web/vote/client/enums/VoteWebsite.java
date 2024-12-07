package fr.pacifista.api.web.vote.client.enums;

import fr.pacifista.api.web.vote.client.dtos.VoteWebsiteDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public enum VoteWebsite {
    /**
     * <a href="https://serveur-minecraft.com/faq/api">Doc</a>
     */
    SERVEUR_MINECRAFT_COM(
            "https://serveur-minecraft.com/4031",
            "https://serveur-minecraft.com/api/1/vote/4031/{playerIp}",
            "Serveur-Minecraft.com",
            3 * 60,
            true
    ),

    TOP_SERVEURS_NET(
            "https://top-serveurs.net/minecraft/vote/pacifista-66978fa1d2710",
            "https://api.top-serveurs.net/v1/votes/check-ip?server_token=PTCXZ2C4OLS1&ip={playerIp}",
            "Top-Serveurs.net",
            60 * 2,
            true
    ),

    /**
     * <a href="https://serveur-prive.net/documentation">Doc</a>
     */
    SERVEUR_PRIVE_NET(
            "https://serveur-prive.net/minecraft/pacifista",
            "https://serveur-prive.net/api/v1/servers/3RNQ6N1Q/votes/{playerIp}",
            "Serveur-Prive.net",
            60 + 30,
            true
    ),

    /**
     * <a href="https://www.serveurs-minecraft.org/api.php?id=56893">Doc</a>
     */
    SERVEUR_MINECRAFT_ORG(
            "https://www.serveurs-minecraft.org/vote.php?id=56893",
            "https://www.serveurs-minecraft.org/api/is_valid_vote.php?id=56893&ip={playerIp}&duration=1440",
            "Serveurs-Minecraft.org",
            24 * 60,
            true
    );

    private final String urlVote;
    private final String apiUrl;
    private final String displayName;
    private final int coolDownInMinutes;
    private final boolean enabled;

    public VoteWebsiteDTO toDTO() {
        return new VoteWebsiteDTO(
                this.name(),
                this.displayName,
                this.urlVote,
                this.coolDownInMinutes
        );
    }

    public Date getNextVoteDate() {
        return Date.from(
                Instant.now().plus(this.coolDownInMinutes, ChronoUnit.MINUTES)
        );
    }
}
