package fr.pacifista.api.core.client.enums.mojang.client;

import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import fr.pacifista.api.core.client.enums.mojang.dto.MojangUserNameAndIdDTO;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.lang.Nullable;

public class MojangApiImplClient implements MojangApiClient {
    public static final String MOJANG_API_DOMAIN = "https://api.mojang.com";

    private final MojangApiClient client;

    public MojangApiImplClient() {
        this.client = Feign.builder()
                .contract(new SpringMvcContract())
                .client(new OkHttpClient())
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(MojangApiClient.class, MOJANG_API_DOMAIN);
    }

    @Override
    @Nullable
    public MojangUserNameAndIdDTO getUserIdByUsername(String username) throws ApiException {
        try {
            return client.getUserIdByUsername(username);
        } catch (FeignException e) {
            return handleFeignException(e);
        }
    }

    @Override
    @Nullable
    public MojangUserNameAndIdDTO getUsernameByUuid(String uuid) throws ApiException {
        try {
            return client.getUsernameByUuid(uuid);
        } catch (FeignException e) {
            return handleFeignException(e);
        }
    }

    private MojangUserNameAndIdDTO handleFeignException(final FeignException e) throws ApiException {
        if (e.status() == 204) {
            return null;
        } else if (Integer.toString(e.status()).startsWith("4")) {
            throw new ApiBadRequestException(e.getMessage(), e);
        } else {
            throw new ApiException(e.getMessage(), e);
        }
    }
}
