package fr.pacifista.api.server.jobs.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.core.client.enums.ServerGameMode;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class JobPlayerEntity extends ApiEntity {

    /**
     * The UUID du joueur Minecraft
     */
    @Column(name = "player_uuid", nullable = false)
    private String playerUuid;

    /**
     * Le mode de jeu du job
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "game_mode", nullable = false)
    private ServerGameMode gameMode;

    /**
     * Le nom du métier crée depuis les plugins
     */
    @Column(name = "job_name", nullable = false)
    private String jobName;

}
