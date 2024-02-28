package fr.pacifista.api.server.jobs.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.core.client.enums.ServerGameMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class JobPlayerEntityDTO extends ApiDTO {

    /**
     * The UUID du joueur Minecraft
     */
    @NotBlank(message = "L'uuid du joueur Minecraft est requis")
    private String playerUuid;

    /**
     * Le mode de jeu du job
     */
    @NotNull(message = "Le mode de jeu du job est requis")
    private ServerGameMode gameMode;

    /**
     * Le nom du métier crée depuis les plugins
     */
    @NotBlank(message = "Le nom du métier est requis")
    private String jobName;

}
