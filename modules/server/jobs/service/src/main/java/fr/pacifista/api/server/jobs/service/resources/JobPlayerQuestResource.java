package fr.pacifista.api.server.jobs.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerQuestClient;
import fr.pacifista.api.server.jobs.client.clients.JobPlayerQuestClientImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerQuestDTO;
import fr.pacifista.api.server.jobs.service.services.JobPlayerQuestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + JobPlayerQuestClientImpl.PATH)
public class JobPlayerQuestResource extends ApiResource<JobPlayerQuestDTO, JobPlayerQuestService> implements JobPlayerQuestClient {
    public JobPlayerQuestResource(JobPlayerQuestService jobPlayerQuestService) {
        super(jobPlayerQuestService);
    }
}
