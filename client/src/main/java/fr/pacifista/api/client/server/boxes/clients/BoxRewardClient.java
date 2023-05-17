package fr.pacifista.api.client.server.boxes.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.client.server.boxes.dtos.BoxRewardDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "BoxReward", url = "${pacifista.api.app-domain-url}", path = "/box/rewards")
public interface BoxRewardClient extends CrudClient<BoxRewardDTO> {
}
