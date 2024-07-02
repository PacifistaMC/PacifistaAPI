package fr.pacifista.api.server.essentials.client.ingore_players.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.essentials.client.ingore_players.dtos.PlayerIgnoreDTO;

public class PlayerIgnoreClientImpl extends FeignImpl<PlayerIgnoreDTO, PlayerIgnoreClient> {

    public static final String PATH = "essentials/player-ignore";

    public PlayerIgnoreClientImpl() {
        super(PATH, PlayerIgnoreClient.class);
    }

}
