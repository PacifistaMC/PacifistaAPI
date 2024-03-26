package fr.pacifista.api.server.jobs.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerBlockPlaceDTO;
import fr.pacifista.api.server.jobs.service.entities.JobPlayerBlockPlace;
import fr.pacifista.api.server.jobs.service.mappers.JobPlayerBlockPlaceMapper;
import fr.pacifista.api.server.jobs.service.repositories.JobPlayerBlockPlaceRepository;
import org.springframework.stereotype.Service;

@Service
public class JobPlayerBlockPlaceService extends ApiService<JobPlayerBlockPlaceDTO, JobPlayerBlockPlace, JobPlayerBlockPlaceMapper, JobPlayerBlockPlaceRepository> {
    public JobPlayerBlockPlaceService(JobPlayerBlockPlaceRepository repository, JobPlayerBlockPlaceMapper mapper) {
        super(repository, mapper);
    }
}
