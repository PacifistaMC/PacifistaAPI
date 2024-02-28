package fr.pacifista.api.server.jobs.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Classe qui va représenter un joueur dans le jeu pour les quêtes de jobs
 */
@Getter
@Setter
@Entity(name = "job_player_quest")
public class JobPlayerQuest extends JobPlayerEntity {

    /**
     * Le nom de la quête définie sur le plugin pacifista
     */
    @Column(name = "quest_name", nullable = false)
    private String questName;

    /**
     * Si la quête est terminée ou non
     */
    @Column(name = "finished", nullable = false)
    private Boolean finished;

    /**
     * La date à laquelle la quête a été terminée
     */
    @Column(name = "finished_at")
    private Date finishedAt;

    /**
     * Les données de la quête en JSON, utile quand on veut stocker des données complexes comme des enchantements ou des potions
     */
    @Column(name = "json_quest_data", nullable = false)
    private String jsonQuestData;

}
