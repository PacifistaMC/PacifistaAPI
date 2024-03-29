package fr.pacifista.api.server.sanctions.client.clients;

import com.funixproductions.core.exceptions.ApiException;
import feign.FeignException;
import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.sanctions.client.dtos.SanctionDTO;
import fr.pacifista.api.server.sanctions.client.enums.SanctionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SanctionImplClient extends FeignImpl<SanctionDTO, SanctionClient> implements SanctionClient {
    public SanctionImplClient() {
        super("sanctions", SanctionClient.class);
    }

    @Override
    public SanctionDTO isPlayerSanctioned(@NotBlank String playerUuid, @NotNull SanctionType sanctionType) throws ApiException {
        try {
            return getClient().isPlayerSanctioned(playerUuid, sanctionType);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public SanctionDTO isIpSanctioned(@NotBlank String playerIp, @NotNull SanctionType sanctionType) throws ApiException {
        try {
            return getClient().isIpSanctioned(playerIp, sanctionType);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }
}
