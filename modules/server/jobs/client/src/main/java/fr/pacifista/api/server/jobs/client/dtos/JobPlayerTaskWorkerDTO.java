package fr.pacifista.api.server.jobs.client.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPlayerTaskWorkerDTO extends JobPlayerEntityDTO {

    public JobPlayerTaskWorkerDTO(JobPlayerTaskDTO task,
                                  Integer participationAmount) {
        this.task = task;
        this.participationAmount = participationAmount;
        this.active = false;
    }

    /**
     * The task worker's task.
     */
    @NotNull(message = "La tâche du travailleur est requise")
    private JobPlayerTaskDTO task;

    /**
     * The amount of participation the worker has in the task.
     */
    @NotNull(message = "La quantité de participation du travailleur est requise")
    @Min(value = 0, message = "La quantité de participation du travailleur doit être supérieure ou égale à 0")
    private Integer participationAmount;

    /**
     * If the player is working on it. True if working on it, false if stopped working on it.
     */
    private Boolean active;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
