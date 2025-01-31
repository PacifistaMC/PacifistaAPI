package fr.pacifista.api.server.jobs.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * JobPlayerTask: les tâches à réaliser pour les quêtes données par les joueurs
 */
@Getter
@Setter
@Entity(name = "job_player_task")
public class JobPlayerTask extends JobPlayerEntity {

    /**
     * Combien de ressources sont nécessaires pour terminer la tâche
     */
    @Column(name = "amount_required", nullable = false)
    private Integer amountRequired;

    /**
     * Combien de ressources ont été collectées pour terminer la tâche
     */
    @Column(name = "amount_collected", nullable = false)
    private Integer amountCollected;

    /**
     * Indique si la tache est terminée, utilisée pour la recherche de tâches à compléter en cours
     */
    @Column(name = "is_task_completed", nullable = false)
    private Boolean isTaskCompleted;

    /**
     * Indique le nombre d'items qui ont été récupéré par le joueur qui a lancé la tâche
     */
    @Column(name = "collected_task_items", nullable = false)
    private Integer collectedTaskItems;

    /**
     * Le nom de la ressource à collecter, nom mojang
     */
    @Column(name = "material_name_to_collect", nullable = false)
    private String materialNameToCollect;

    /**
     * Les données de la tâche en JSON, utile quand on veut stocker des données complexes comme des enchantements ou des potions
     */
    @Column(name = "json_task_data", length = 10000000)
    private String jsonTaskData;

    /**
     * Le type de tâche, pour savoir si c'est une tâche de collecte, de craft, de pêche, etc. Utile pour les données complexes
     */
    @Column(name = "task_type", length = 10000000)
    private String taskType;
}
