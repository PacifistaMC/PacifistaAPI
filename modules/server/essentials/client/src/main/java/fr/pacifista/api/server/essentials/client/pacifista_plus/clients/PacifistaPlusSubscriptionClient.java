package fr.pacifista.api.server.essentials.client.pacifista_plus.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.essentials.client.pacifista_plus.dtos.PacifistaPlusSubscriptionDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaPlusSubscriptionClient",
        url = "${pacifista.api.server.essentials.app-domain-url}",
        path = PacifistaPlusSubscriptionClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaPlusSubscriptionClient extends CrudClient<PacifistaPlusSubscriptionDTO> {
}
