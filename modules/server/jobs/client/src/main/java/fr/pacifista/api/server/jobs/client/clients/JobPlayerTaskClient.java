package fr.pacifista.api.server.jobs.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "JobPlayerTask",
        url = "${pacifista.api.server.jobs.app-domain-url}",
        path = JobPlayerTaskClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface JobPlayerTaskClient extends CrudClient<JobPlayerTaskDTO> {
}
