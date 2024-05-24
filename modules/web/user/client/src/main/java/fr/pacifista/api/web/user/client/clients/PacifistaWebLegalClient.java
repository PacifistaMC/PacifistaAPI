package fr.pacifista.api.web.user.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaWebLegalClient",
        url = "${pacifista.api.web.user.app-domain-url}",
        path = "kubeinternal/web/user/legal-document"
)
public interface PacifistaWebLegalClient extends CrudClient<PacifistaWebLegalDTO> {
}
