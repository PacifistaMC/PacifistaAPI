package fr.pacifista.api.client.web.news.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.web.news.dtos.PacifistaNewsDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PacifistaNews", url = "${pacifista.api.app-domain-url}", path = "/web/news")
public interface PacifistaNewsClient extends CrudClient<PacifistaNewsDTO> {
}
