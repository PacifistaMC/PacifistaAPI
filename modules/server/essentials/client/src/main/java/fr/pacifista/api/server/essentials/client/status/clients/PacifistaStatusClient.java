package fr.pacifista.api.server.essentials.client.status.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaStatusClient",
        url = "${pacifista.api.server.essentials.app-domain-url}",
        path = PacifistaStatusImplClient.PATH
)
public interface PacifistaStatusClient extends PacifistaStatus {
}
