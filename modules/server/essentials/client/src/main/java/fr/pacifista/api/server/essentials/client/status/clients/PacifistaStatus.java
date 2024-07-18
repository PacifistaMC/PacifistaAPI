package fr.pacifista.api.server.essentials.client.status.clients;

import fr.pacifista.api.server.essentials.client.status.dtos.PacifistaServerInfoDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

public interface PacifistaStatus {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    PacifistaServerInfoDTO getServerInfo();

}
