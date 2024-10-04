package fr.pacifista.api.server.warps.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.warps.client.dtos.WarpConfigDTO;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import fr.pacifista.api.server.warps.client.dtos.WarpPlayerInteractionDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "WarpClient",
        url = "${pacifista.api.server.warps.app-domain-url}",
        path = "/" + WarpClientImpl.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface WarpClient extends CrudClient<WarpDTO> {

    @PutMapping("/config")
    WarpConfigDTO updateConfig(@RequestBody @Valid WarpConfigDTO warpConfigDTO);

    @PostMapping("/like")
    WarpPlayerInteractionDTO likeWarp(
            @RequestParam(value = "warp_id") UUID warpId,
            @RequestParam(value = "player_id") UUID playerId
    );

    @PostMapping("/use")
    WarpPlayerInteractionDTO useWarp(
            @RequestParam(value = "warp_id") UUID warpId,
            @RequestParam(value = "player_id") UUID playerId
    );

    @GetMapping("/likes")
    PageDTO<WarpPlayerInteractionDTO> getWarpLikes(
            @RequestParam(value = "warp_id") UUID warpId,
            @RequestParam(value = "page", defaultValue = "0") int page
    );

    @GetMapping("/uses")
    PageDTO<WarpPlayerInteractionDTO> getWarpUses(
            @RequestParam(value = "warp_id") UUID warpId,
            @RequestParam(value = "page", defaultValue = "0") int page
    );

}
