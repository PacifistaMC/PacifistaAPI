package fr.pacifista.api.service.web.news.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.client.web.news.clients.PacifistaNewsClient;
import fr.pacifista.api.client.web.news.dtos.PacifistaNewsDTO;
import fr.pacifista.api.service.web.news.services.PacifistaNewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("web/news")
public class PacifistaNewsResource extends ApiResource<PacifistaNewsDTO, PacifistaNewsService> implements PacifistaNewsClient {
    public PacifistaNewsResource(PacifistaNewsService pacifistaNewsService) {
        super(pacifistaNewsService);
    }
}
