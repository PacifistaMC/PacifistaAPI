package fr.pacifista.api.server.essentials.client.discord.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.essentials.client.discord.dtos.DiscordLinkDTO;

public class DiscordLinkClientImpl extends FeignImpl<DiscordLinkDTO, DiscordLinkClient> {

    public static final String PATH = "essentials/discord-link";

    public DiscordLinkClientImpl() {
        super(PATH, DiscordLinkClient.class);
    }

}
