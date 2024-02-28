package fr.pacifista.api.server.jobs.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerQuestDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "JobPlayerQuest",
        url = "${pacifista.api.server.jobs.app-domain-url}",
        path = JobPlayerQuestClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface JobPlayerQuestClient extends CrudClient<JobPlayerQuestDTO> {
}
