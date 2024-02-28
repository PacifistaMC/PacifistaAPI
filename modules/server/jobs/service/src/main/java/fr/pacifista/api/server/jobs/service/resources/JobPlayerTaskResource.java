package fr.pacifista.api.server.jobs.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerTaskClient;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerTaskClientImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskDTO;
import fr.pacifista.api.server.jobs.service.services.JobPlayerTaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + JobPlayerTaskClientImpl.PATH)
public class JobPlayerTaskResource extends ApiResource<JobPlayerTaskDTO, JobPlayerTaskService> implements JobPlayerTaskClient {
    public JobPlayerTaskResource(JobPlayerTaskService jobPlayerTaskService) {
        super(jobPlayerTaskService);
    }
}
