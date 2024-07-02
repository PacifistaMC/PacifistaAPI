package fr.pacifista.api.server.essentials.client.pacifista_plus.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.essentials.client.pacifista_plus.dtos.PacifistaPlusSubscriptionDTO;

public class PacifistaPlusSubscriptionClientImpl extends FeignImpl<PacifistaPlusSubscriptionDTO, PacifistaPlusSubscriptionClient> {

    public static final String PATH = "essentials/pacifista-plus-subscription";

    public PacifistaPlusSubscriptionClientImpl() {
        super(PATH, PacifistaPlusSubscriptionClient.class);
    }

}
