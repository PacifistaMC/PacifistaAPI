package fr.pacifista.api.web.vote.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO extends ApiDTO {

    /**
     * Pseudo Minecraft du joueur.
     */
    @NotBlank(message = "Le nom d'utilisateur Minecraft est obligatoire")
    private String username;

    /**
     * Site de vote.
     */
    @NotNull(message = "Le site de vote est obligatoire")
    private VoteWebsite voteWebsite;

    /**
     * Date de validation du vote. Null si le vote n'a pas encore été validé.
     */
    @Nullable
    private Date voteValidationDate;

    /**
     * Date du prochain vote. Null si le vote n'a pas encore été validé.
     */
    @Nullable
    private Date nextVoteDate;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final VoteDTO other) {
            return super.equals(obj) && this.username.equals(other.username) && this.voteWebsite.equals(other.voteWebsite);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.username.hashCode() + this.voteWebsite.hashCode();
    }
}
