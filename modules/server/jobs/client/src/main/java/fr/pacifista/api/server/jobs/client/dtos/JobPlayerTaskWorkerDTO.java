package fr.pacifista.api.server.jobs.client.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPlayerTaskWorkerDTO extends JobPlayerEntityDTO {

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

}
