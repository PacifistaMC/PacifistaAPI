package fr.pacifista.api.server.jobs.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerDTO;
import fr.pacifista.api.server.jobs.service.entities.JobPlayer;
import fr.pacifista.api.server.jobs.service.mappers.JobPlayerMapper;
import fr.pacifista.api.server.jobs.service.repositories.JobPlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class JobPlayerService extends ApiService<JobPlayerDTO, JobPlayer, JobPlayerMapper, JobPlayerRepository> {

    public JobPlayerService(JobPlayerRepository repository, JobPlayerMapper mapper) {
        super(repository, mapper);
    }

}
