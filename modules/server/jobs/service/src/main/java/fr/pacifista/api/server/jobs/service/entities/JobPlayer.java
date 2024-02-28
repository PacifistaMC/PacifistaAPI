package fr.pacifista.api.server.jobs.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe qui va représenter un joueur dans le jeu pour les métiers
 */
@Getter
@Setter
@Entity(name = "job_player")
public class JobPlayer extends JobPlayerEntity {

    /**
     * L'expérience du joueur job
     */
    @Column(name = "exp", nullable = false)
    private Integer exp;

    /**
     * Le niveau du joueur job
     */
    @Column(name = "level", nullable = false)
    private Integer level;

    /**
     * Les points du joueur job pour la boutique de jobs
     */
    @Column(name = "points", nullable = false)
    private Integer points;

    /**
     * Si le joueur est actif dans le job, ils peuvent quitter et changer de métiers, voir en avoir plusieurs
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

}
