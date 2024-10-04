package fr.pacifista.api.server.warps.client.clients;

import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.exceptions.ApiException;
import feign.FeignException;
import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.warps.client.dtos.WarpConfigDTO;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;
import fr.pacifista.api.server.warps.client.dtos.WarpPlayerInteractionDTO;

import java.util.UUID;

public class WarpClientImpl extends FeignImpl<WarpDTO, WarpClient> implements WarpClient {

    public static final String PATH = "warps";

    public WarpClientImpl() {
        super(PATH, WarpClient.class);
    }

    @Override
    public WarpConfigDTO updateConfig(WarpConfigDTO warpConfigDTO) throws ApiException {
        try {
            return super.getClient().updateConfig(warpConfigDTO);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public WarpPlayerInteractionDTO likeWarp(UUID warpId, UUID playerId) {
        try {
            return super.getClient().likeWarp(warpId, playerId);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public WarpPlayerInteractionDTO useWarp(UUID warpId, UUID playerId) {
        try {
            return super.getClient().useWarp(warpId, playerId);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public PageDTO<WarpPlayerInteractionDTO> getWarpLikes(UUID warpId, int page) {
        try {
            return super.getClient().getWarpLikes(warpId, page);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public PageDTO<WarpPlayerInteractionDTO> getWarpUses(UUID warpId, int page) {
        try {
            return super.getClient().getWarpUses(warpId, page);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }
}
