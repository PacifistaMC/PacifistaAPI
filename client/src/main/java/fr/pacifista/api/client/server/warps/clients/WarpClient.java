package fr.pacifista.api.client.server.warps.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.warps.dtos.WarpDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Warps", url = "${pacifista.api.app-domain-url}", path = "/warps")
public interface WarpClient extends CrudClient<WarpDTO> {
}
