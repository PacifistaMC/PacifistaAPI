package fr.pacifista.api.server.essentials.service.status.clients;

import fr.pacifista.api.server.essentials.client.status.dtos.PacifistaServerInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "PacifistaInternalStatusClient",
        url = "${pacifista.server.status.internal.ip}:${pacifista.server.status.internal.port}"
)
public interface PacifistaInternalStatusClient {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    PacifistaServerInfoDTO getServerInfo();

}
