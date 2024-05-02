package fr.pacifista.api.web.user.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;

public class PacifistaWebUserLinkClientImpl extends FeignImpl<PacifistaWebUserLinkDTO, PacifistaWebUserLinkClient> {

    public static final String PATH = "web/user/link";
    public static final String INTERNAL_PATH = "kubeinternal/web/user/link";

    public PacifistaWebUserLinkClientImpl() {
        super(PATH, PacifistaWebUserLinkClient.class);
    }

}
