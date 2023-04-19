package fr.pacifista.api.client.boxes.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.boxes.dtos.BoxRewardDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "BoxReward", url = "${pacifista.api.app-domain-url}", path = "/box/rewards")
public interface BoxRewardClient extends CrudClient<BoxRewardDTO> {
}
