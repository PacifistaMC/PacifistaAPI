package fr.pacifista.api.server.jobs.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.jobs.service.entities.JobPlayerQuest;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPlayerQuestRepository extends ApiRepository<JobPlayerQuest> {
}
