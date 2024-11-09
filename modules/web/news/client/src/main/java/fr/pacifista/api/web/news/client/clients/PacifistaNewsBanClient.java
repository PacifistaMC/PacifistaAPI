package fr.pacifista.api.web.news.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.web.news.client.dtos.ban.PacifistaNewsBanDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaNewsComments",
        url = "${pacifista.web.news.api.app-domain-url}",
        path = "/web/news/bans",
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaNewsBanClient extends CrudClient<PacifistaNewsBanDTO> {
}
