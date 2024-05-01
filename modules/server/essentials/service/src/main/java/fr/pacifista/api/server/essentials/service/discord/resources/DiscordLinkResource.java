package fr.pacifista.api.server.essentials.service.discord.resources;


import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.essentials.client.discord.clients.DiscordLinkClientImpl;
import fr.pacifista.api.server.essentials.client.discord.dtos.DiscordLinkDTO;
import fr.pacifista.api.server.essentials.service.discord.services.DiscordLinkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + DiscordLinkClientImpl.PATH)
public class DiscordLinkResource extends ApiResource<DiscordLinkDTO, DiscordLinkService> {

    public DiscordLinkResource(DiscordLinkService service) {
        super(service);
    }

}
