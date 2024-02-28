package fr.pacifista.api.server.jobs.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskDTO;
import fr.pacifista.api.server.jobs.service.entities.JobPlayerTask;
import fr.pacifista.api.server.jobs.service.mappers.JobPlayerTaskMapper;
import fr.pacifista.api.server.jobs.service.repositories.JobPlayerTaskRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPlayerTaskService extends ApiService<JobPlayerTaskDTO, JobPlayerTask, JobPlayerTaskMapper, JobPlayerTaskRepository> {

    public JobPlayerTaskService(JobPlayerTaskRepository repository, JobPlayerTaskMapper mapper) {
        super(repository, mapper);
    }

    public Iterable<JobPlayerTask> findTaskByUuid(final @NonNull List<String> uuid) {
        return getRepository().findAllByUuidIn(uuid);
    }

}
