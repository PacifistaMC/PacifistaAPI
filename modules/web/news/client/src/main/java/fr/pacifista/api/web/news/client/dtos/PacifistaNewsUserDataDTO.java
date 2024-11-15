package fr.pacifista.api.web.news.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class PacifistaNewsUserDataDTO extends ApiDTO {

    /**
     * La news à laquelle ce commentaire répond
     */
    @NotNull
    private PacifistaNewsDTO news;

    /**
     * Nom d'utilisateur Minecraft du joueur
     */
    private String minecraftUsername;

    /**
     * UUID du compte ID sur la funixproductions api
     */
    private UUID funixProdUserId;

}
