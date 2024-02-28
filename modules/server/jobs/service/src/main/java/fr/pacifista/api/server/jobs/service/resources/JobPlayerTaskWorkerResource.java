package fr.pacifista.api.server.jobs.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerTaskWorkerClient;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerTaskWorkerClientImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskWorkerDTO;
import fr.pacifista.api.server.jobs.service.services.JobPlayerTaskWorkerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + JobPlayerTaskWorkerClientImpl.PATH)
public class JobPlayerTaskWorkerResource extends ApiResource<JobPlayerTaskWorkerDTO, JobPlayerTaskWorkerService> implements JobPlayerTaskWorkerClient {
    public JobPlayerTaskWorkerResource(JobPlayerTaskWorkerService jobPlayerTaskWorkerService) {
        super(jobPlayerTaskWorkerService);
    }
}
