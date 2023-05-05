package fr.pacifista.api.service.support.tickets.services;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class REMOVEDTO {

    @Getter
    @Setter
    public static class Notification {
        private String title;
        private String body;
        private String url;
    }

    private Notification notification;

    private String to;

}
