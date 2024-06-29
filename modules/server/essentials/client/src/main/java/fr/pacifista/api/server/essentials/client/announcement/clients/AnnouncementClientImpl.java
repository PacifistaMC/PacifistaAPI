package fr.pacifista.api.server.essentials.client.announcement.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.essentials.client.announcement.dtos.AnnouncementDTO;

public class AnnouncementClientImpl extends FeignImpl<AnnouncementDTO, AnnouncementClient> {

    public static final String PATH = "essentials/announcements";

    public AnnouncementClientImpl() {
        super(PATH, AnnouncementClient.class);
    }

}
