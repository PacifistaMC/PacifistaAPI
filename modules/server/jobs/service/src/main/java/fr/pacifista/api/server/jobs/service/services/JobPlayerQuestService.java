package fr.pacifista.api.server.jobs.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerQuestDTO;
import fr.pacifista.api.server.jobs.service.entities.JobPlayerQuest;
import fr.pacifista.api.server.jobs.service.mappers.JobPlayerQuestMapper;
import fr.pacifista.api.server.jobs.service.repositories.JobPlayerQuestRepository;
import org.springframework.stereotype.Service;

@Service
public class JobPlayerQuestService extends ApiService<JobPlayerQuestDTO, JobPlayerQuest, JobPlayerQuestMapper, JobPlayerQuestRepository> {

    public JobPlayerQuestService(JobPlayerQuestRepository repository,
                                 JobPlayerQuestMapper mapper) {
        super(repository, mapper);
    }

}
