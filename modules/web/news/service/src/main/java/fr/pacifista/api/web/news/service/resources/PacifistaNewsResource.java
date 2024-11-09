package fr.pacifista.api.web.news.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.web.news.client.clients.PacifistaNewsClient;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.service.services.PacifistaNewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/news")
public class PacifistaNewsResource extends ApiResource<PacifistaNewsDTO, PacifistaNewsService> implements PacifistaNewsClient {
    public PacifistaNewsResource(PacifistaNewsService pacifistaNewsService) {
        super(pacifistaNewsService);
    }
}
