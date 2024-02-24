package fr.pacifista.api.server.box.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.box.client.dtos.BoxRewardDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "BoxReward",
        url = "${pacifista.api.server.box.app-domain-url}",
        path = BoxRewardImplClient.PATH,
        configuration = FeignTokenInterceptor.class
)
public interface BoxRewardClient extends CrudClient<BoxRewardDTO> {
}
