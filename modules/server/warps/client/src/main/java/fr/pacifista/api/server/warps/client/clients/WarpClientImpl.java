package fr.pacifista.api.server.warps.client.clients;

import com.funixproductions.core.exceptions.ApiException;
import feign.FeignException;
import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.warps.client.dtos.WarpConfigDTO;
import fr.pacifista.api.server.warps.client.dtos.WarpDTO;

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

}
