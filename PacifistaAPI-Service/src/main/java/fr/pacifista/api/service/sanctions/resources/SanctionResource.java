package fr.pacifista.api.service.sanctions.resources;

import fr.funixgaming.api.core.crud.resources.ApiResource;
import fr.pacifista.api.client.modules.sanctions.clients.SanctionClient;
import fr.pacifista.api.client.modules.sanctions.dtos.SanctionDTO;
import fr.pacifista.api.client.modules.sanctions.enums.SanctionType;
import fr.pacifista.api.service.sanctions.services.SanctionService;
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
