package fr.pacifista.api.web.user.client.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalUserDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "PacifistaWebLegalUserClient",
        url = "${pacifista.api.web.user.app-domain-url}",
        path = "kubeinternal/web/user/legal-document-user"
)
public interface PacifistaWebLegalUserClient extends CrudClient<PacifistaWebLegalUserDTO> {
}
