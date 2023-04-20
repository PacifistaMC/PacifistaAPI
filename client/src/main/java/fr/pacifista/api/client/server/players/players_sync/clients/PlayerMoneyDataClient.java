package fr.pacifista.api.client.players.players_sync.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.players.players_sync.dtos.PlayerMoneyDataDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "PlayerMoney", url = "${pacifista.api.app-domain-url}", path = "/playersync/money")
public interface PlayerMoneyDataClient extends CrudClient<PlayerMoneyDataDTO> {
}
