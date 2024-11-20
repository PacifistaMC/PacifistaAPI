package fr.pacifista.api.web.news.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.web.news.client.dtos.ban.PacifistaNewsBanDTO;
import fr.pacifista.api.web.news.service.services.ban.PacifistaNewsBanCrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/news/bans")
public class PacifistaNewsBanResource extends ApiResource<PacifistaNewsBanDTO, PacifistaNewsBanCrudService> {

    public PacifistaNewsBanResource(PacifistaNewsBanCrudService service) {
        super(service);
    }

}
