package fr.pacifista.api.server.jobs.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "job_player_task_worker")
public class JobPlayerTaskWorker extends JobPlayerEntity {

    /**
     * The task worker's task.
     */
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private JobPlayerTask task;

    /**
     * The amount of participation the worker has in the task.
     */
    @Column(name = "participation_amount", nullable = false)
    private Integer participationAmount;

}
