package fr.pacifista.api.server.jobs.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskWorkerDTO;
import fr.pacifista.api.server.jobs.service.entities.JobPlayerTask;
import fr.pacifista.api.server.jobs.service.entities.JobPlayerTaskWorker;
import fr.pacifista.api.server.jobs.service.mappers.JobPlayerTaskWorkerMapper;
import fr.pacifista.api.server.jobs.service.repositories.JobPlayerTaskWorkerRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobPlayerTaskWorkerService extends ApiService<JobPlayerTaskWorkerDTO, JobPlayerTaskWorker, JobPlayerTaskWorkerMapper, JobPlayerTaskWorkerRepository> {

    private final JobPlayerTaskService jobPlayerTaskService;

    public JobPlayerTaskWorkerService(JobPlayerTaskWorkerRepository repository,
                                      JobPlayerTaskWorkerMapper mapper,
                                      JobPlayerTaskService jobPlayerTaskService) {
        super(repository, mapper);
        this.jobPlayerTaskService = jobPlayerTaskService;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<JobPlayerTaskWorker> workerTasks) {
        if (isCreatingEntityMode(workerTasks)) {
            final List<String> uuids = getUuidListTaskFromTasksWorker(workerTasks);

            final Iterable<JobPlayerTask> tasks = jobPlayerTaskService.findTaskByUuid(uuids);
            for (final JobPlayerTaskWorker worker : workerTasks) {
                if (worker.getId() == null) {
                    worker.setActive(true);
                }

                for (final JobPlayerTask task : tasks) {
                    if (worker.getTask().getUuid().equals(task.getUuid())) {
                        worker.setTask(task);
                    }
                }
            }
        }

    }

    private List<String> getUuidListTaskFromTasksWorker(@NonNull Iterable<JobPlayerTaskWorker> workers) {
        final List<String> uuids = new ArrayList<>();

        for (JobPlayerTaskWorker worker : workers) {
            if (worker.getTask() == null || worker.getTask().getUuid() == null) {
                throw new ApiBadRequestException("Vous devez spécifier une tache de job avec un id dans la requête.");
            }
            uuids.add(worker.getTask().getUuid().toString());
        }
        return uuids;
    }

    private boolean isCreatingEntityMode(@NonNull Iterable<JobPlayerTaskWorker> entity) {
        for (JobPlayerTaskWorker worker : entity) {
            if (worker.getId() != null || worker.getUuid() != null) {
                return false;
            }
        }
        return true;
    }
}
