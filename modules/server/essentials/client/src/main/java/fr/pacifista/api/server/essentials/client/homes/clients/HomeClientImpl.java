package fr.pacifista.api.server.essentials.client.homes.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.essentials.client.homes.dtos.HomeDTO;

public class HomeClientImpl extends FeignImpl<HomeDTO, HomeClient> {

    public static final String PATH = "essentials/home";

    public HomeClientImpl() {
        super(PATH, HomeClient.class);
    }
}
