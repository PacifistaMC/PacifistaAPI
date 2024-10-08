package fr.pacifista.api.server.warps.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.warps.client.dtos.WarpConfigDTO;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "WarpClient",
        url = "${pacifista.api.server.warps.app-domain-url}",
        path = "/" + WarpClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface WarpClient extends CrudClient<WarpDTO> {

    @PutMapping("/config")
    WarpConfigDTO updateConfig(@RequestBody @Valid WarpConfigDTO warpConfigDTO);

}
