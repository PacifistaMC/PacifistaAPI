package fr.pacifista.api.server.jobs.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerBlockPlaceDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "JobPlayerBlockPlace",
        url = "${pacifista.api.server.jobs.app-domain-url}",
        path = JobPlayerBlockPlaceClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface JobPlayerBlockPlaceClient extends CrudClient<JobPlayerBlockPlaceDTO> {
}
