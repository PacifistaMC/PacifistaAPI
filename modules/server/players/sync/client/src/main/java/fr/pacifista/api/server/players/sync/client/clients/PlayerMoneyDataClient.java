package fr.pacifista.api.server.players.sync.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.players.sync.client.dtos.PlayerMoneyDataDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PlayerMoney",
        url = "${pacifista.api.server.playersync.app-domain-url}",
        path = "/playersync/money",
        configuration = FeignTokenInterceptor.class
)
public interface PlayerMoneyDataClient extends CrudClient<PlayerMoneyDataDTO> {
}
