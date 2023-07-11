package fr.pacifista.api.web.news.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.enums.clients.FeignTokenInterceptor;
import fr.pacifista.api.web.news.client.dtos.PacifistaNewsDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PacifistaNews", url = "${pacifista.web.news.api.app-domain-url}", path = "/web/news", configuration = FeignTokenInterceptor.class)
public interface PacifistaNewsClient extends CrudClient<PacifistaNewsDTO> {
}
