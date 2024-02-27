package fr.pacifista.api.server.jobs.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "JobPlayer",
        url = "${pacifista.api.server.jobs.app-domain-url}",
        path = JobPlayerClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface JobPlayerClient extends CrudClient<JobPlayerDTO> {
}
