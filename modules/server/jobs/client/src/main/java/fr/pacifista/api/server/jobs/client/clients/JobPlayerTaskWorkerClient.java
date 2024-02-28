package fr.pacifista.api.server.jobs.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskWorkerDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "JobPlayerTaskWorker",
        url = "${pacifista.api.server.jobs.app-domain-url}",
        path = JobPlayerTaskWorkerClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface JobPlayerTaskWorkerClient extends CrudClient<JobPlayerTaskWorkerDTO> {
}
