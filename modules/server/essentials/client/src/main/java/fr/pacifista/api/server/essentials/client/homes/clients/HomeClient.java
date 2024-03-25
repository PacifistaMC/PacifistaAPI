package fr.pacifista.api.server.essentials.client.homes.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.essentials.client.homes.dtos.HomeDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "HomeClient",
        url = "${pacifista.api.server.essentials.app-domain-url}",
        path = HomeClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface HomeClient extends CrudClient<HomeDTO> {
}
