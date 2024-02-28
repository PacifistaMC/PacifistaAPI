package fr.pacifista.api.server.jobs.client.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

/**
 * JobPlayerTask: les tâches à réaliser pour les quêtes données par les joueurs
 */
@Getter
@Setter
public class JobPlayerTaskDTO extends JobPlayerEntityDTO {

    /**
     * Combien de ressources sont nécessaires pour terminer la tâche
     */
    @NotNull(message = "La quantité de ressources requise est requise")
    @Min(value = 1, message = "La quantité de ressources requise doit être supérieure ou égale à 1")
    private Integer amountRequired;

    /**
     * Combien de ressources ont été collectées pour terminer la tâche
     */
    @NotNull(message = "La quantité de ressources collectées est requise")
    @Min(value = 0, message = "La quantité de ressources collectées doit être supérieure ou égale à 0")
    private Integer amountCollected;

    /**
     * Le nom de la ressource à collecter, nom mojang
     */
    @NotNull(message = "Le nom de la ressource à collecter est requis")
    private String materialNameToCollect;

    /**
     * Les données de la tâche en JSON, utile quand on veut stocker des données complexes comme des enchantements ou des potions
     */
    @Nullable
    private String jsonTaskData;

    /**
     * Le type de tâche, pour savoir si c'est une tâche de collecte, de craft, de pêche, etc. Utile pour les données complexes
     */
    @Nullable
    private String taskType;

}
