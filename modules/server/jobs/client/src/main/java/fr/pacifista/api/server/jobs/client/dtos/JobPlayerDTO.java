package fr.pacifista.api.server.jobs.client.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe qui va représenter un joueur dans le jeu pour les métiers
 */
@Getter
@Setter
public class JobPlayerDTO extends JobPlayerEntityDTO {

    /**
     * L'expérience du joueur job
     */
    @NotNull(message = "L'expérience du joueur job est requise")
    @Min(value = 0, message = "L'expérience du joueur job doit être supérieure ou égale à 0")
    private Double exp;

    /**
     * Le niveau du joueur job
     */
    @NotNull(message = "Le niveau du joueur job est requis")
    @Min(value = 0, message = "Le niveau du joueur job doit être supérieur ou égal à 0")
    private Integer level;

    /**
     * Les points du joueur job pour la boutique de jobs
     */
    @NotNull(message = "Les points du joueur job pour la boutique de jobs sont requis")
    @Min(value = 0, message = "Les points du joueur job pour la boutique de jobs doivent être supérieurs ou égaux à 0")
    private Integer points;

    /**
     * Si le joueur est actif dans le job, ils peuvent quitter et changer de métiers, voir en avoir plusieurs
     */
    @NotNull(message = "Si le joueur est actif dans le job est requis")
    private Boolean isActive;

}
