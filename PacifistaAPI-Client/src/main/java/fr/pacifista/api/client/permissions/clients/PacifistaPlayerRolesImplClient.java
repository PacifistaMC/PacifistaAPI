package fr.pacifista.api.client.permissions.clients;

import feign.FeignException;
import fr.funixgaming.api.core.exceptions.ApiException;
import fr.pacifista.api.client.core.utils.feign_impl.FeignImpl;
import fr.pacifista.api.client.permissions.dtos.PacifistaPlayerRoleDTO;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class PacifistaPlayerRolesImplClient extends FeignImpl<PacifistaPlayerRoleDTO, PacifistaPlayerRolesClient> implements PacifistaPlayerRolesClient {
    public PacifistaPlayerRolesImplClient() {
        super("gameroles/player", PacifistaPlayerRolesClient.class);
    }

    @Override
    public List<PacifistaPlayerRoleDTO> getPlayerRoles(@NotBlank String playerUuid) throws ApiException {
        try {
            return super.getClient().getPlayerRoles(playerUuid);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }
}
