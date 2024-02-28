package fr.pacifista.api.server.jobs.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.jobs.service.entities.JobPlayerTask;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPlayerTaskRepository extends ApiRepository<JobPlayerTask> {
}
