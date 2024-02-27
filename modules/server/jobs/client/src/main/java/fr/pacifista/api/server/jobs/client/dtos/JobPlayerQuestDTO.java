package fr.pacifista.api.server.jobs.client.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * Classe qui va représenter un joueur dans le jeu pour les quêtes de jobs
 */
@Getter
@Setter
public class JobPlayerQuestDTO extends JobPlayerEntityDTO {

    /**
     * Le nom de la quête définie sur le plugin pacifista
     */
    @NotBlank(message = "Le nom de la quête est requis")
    private String questName;

    /**
     * Si la quête est terminée ou non
     */
    @Column(name = "finished", nullable = false)
    private Boolean finished;

    /**
     * La date à laquelle la quête a été terminée
     */
    @Nullable
    private Date finishedAt;

    /**
     * Les données de la quête en JSON, utile quand on veut stocker des données complexes comme des enchantements ou des potions
     */
    @NotBlank(message = "Les données de la quête sont requises")
    private String jsonQuestData;

}
