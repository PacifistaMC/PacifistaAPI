package fr.pacifista.api.server.sanctions.service.resources;

import com.funixproductions.core.crud.resources.ApiResource;
import fr.pacifista.api.server.sanctions.client.clients.SanctionClient;
import fr.pacifista.api.server.sanctions.client.dtos.SanctionDTO;
import fr.pacifista.api.server.sanctions.client.enums.SanctionType;
import fr.pacifista.api.server.sanctions.service.services.SanctionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sanctions")
public class SanctionResource extends ApiResource<SanctionDTO, SanctionService> implements SanctionClient {

    public SanctionResource(SanctionService sanctionService) {
        super(sanctionService);
    }

    @Override
    public SanctionDTO isPlayerSanctioned(String playerUuid, SanctionType sanctionType) {
        return super.getService().isPlayerSanctioned(playerUuid, sanctionType);
    }

    @Override
    public SanctionDTO isIpSanctioned(String playerIp, SanctionType sanctionType) {
        return super.getService().isIpSanctioned(playerIp, sanctionType);
    }
}
