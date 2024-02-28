package fr.pacifista.api.server.jobs.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerClient;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerClientImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerDTO;
import fr.pacifista.api.server.jobs.service.services.JobPlayerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + JobPlayerClientImpl.PATH)
public class JobPlayerResource extends ApiResource<JobPlayerDTO, JobPlayerService> implements JobPlayerClient {
    public JobPlayerResource(JobPlayerService jobPlayerService) {
        super(jobPlayerService);
    }
}
