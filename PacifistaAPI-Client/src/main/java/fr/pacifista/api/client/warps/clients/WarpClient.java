package fr.pacifista.api.client.warps.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.warps.dtos.WarpDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Warps", url = "${pacifista.api.app-domain-url}", path = "/warps")
public interface WarpClient extends CrudClient<WarpDTO> {
}
