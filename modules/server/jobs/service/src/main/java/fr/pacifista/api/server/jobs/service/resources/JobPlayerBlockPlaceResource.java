package fr.pacifista.api.server.jobs.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerBlockPlaceClientImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerBlockPlaceDTO;
import fr.pacifista.api.server.jobs.service.services.JobPlayerBlockPlaceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + JobPlayerBlockPlaceClientImpl.PATH)
public class JobPlayerBlockPlaceResource extends ApiResource<JobPlayerBlockPlaceDTO, JobPlayerBlockPlaceService> {
    public JobPlayerBlockPlaceResource(JobPlayerBlockPlaceService jobPlayerBlockPlaceService) {
        super(jobPlayerBlockPlaceService);
    }
}
