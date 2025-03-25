package fr.pacifista.api.core.client.mojang.clients;

import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import fr.pacifista.api.core.client.mojang.dto.MojangUserNameAndIdDTO;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;

public class MojangApiImplClient {
    public static final String MOJANG_MINECRAFT_SERVICES_API_DOMAIN = "https://api.mojang.com/";
    public static final String MOJANG_SESSION_SERVER_API_DOMAIN = "https://sessionserver.mojang.com/";

    private final MojangApiProfileClient profileClient;
    private final MojangApiSessionClient sessionClient;

    public MojangApiImplClient() {
        this.profileClient = Feign.builder()
                .contract(new SpringMvcContract())
                .client(new OkHttpClient())
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(MojangApiProfileClient.class, MOJANG_MINECRAFT_SERVICES_API_DOMAIN);

        this.sessionClient = Feign.builder()
                .contract(new SpringMvcContract())
                .client(new OkHttpClient())
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(MojangApiSessionClient.class, MOJANG_SESSION_SERVER_API_DOMAIN);
    }

    /**
     * Get player uuid from player name, asks first the database and after mojang api
     * @param username player name
     * @return MojangUserNameAndIdDTO if the player has made a connexion on Mojang, null if not found
     * @throws ApiException on error
     */
    @Nullable
    public final MojangUserNameAndIdDTO getUserIdByUsername(@NonNull final String username) throws ApiException {
        final List<MojangUserNameAndIdDTO> result = getUserIdByUsername(List.of(username));

        if (result.isEmpty()) {
            return null;
        } else {
            final MojangUserNameAndIdDTO res = result.get(0);

            if (res.getName().equalsIgnoreCase(username)) {
                return res;
            } else {
                return null;
            }
        }
    }

    /**
     * Get player uuid from player name, asks first the database and after mojang api
     * @param usernames List of usernames
     * @return List of MojangUserNameAndIdDTO, only contains the players that have made a connexion on Mojang
     * @throws ApiException on error
     */
    public final List<MojangUserNameAndIdDTO> getUserIdByUsername(@NonNull final List<String> usernames) throws ApiException {
        try {
            return profileClient.getUserIdByUsername(usernames);
        } catch (FeignException e) {
            if (e.status() == HttpStatus.NO_CONTENT.value() || e.status() == HttpStatus.NOT_FOUND.value()) {
                return Collections.emptyList();
            } else if (Integer.toString(e.status()).startsWith("4")) {
                throw new ApiBadRequestException(e.getMessage(), e);
            } else {
                throw new ApiException(e.getMessage(), e);
            }
        }
    }

    @Nullable
    public final MojangUserNameAndIdDTO getUsernameByUuid(@NonNull final String uuid) throws ApiException {
        try {
            return this.sessionClient.getUsernameByUuid(uuid);
        } catch (FeignException e) {
            if (e.status() == HttpStatus.NO_CONTENT.value() || e.status() == HttpStatus.NOT_FOUND.value()) {
                return null;
            } else if (Integer.toString(e.status()).startsWith("4")) {
                throw new ApiBadRequestException(e.getMessage(), e);
            } else {
                throw new ApiException(e.getMessage(), e);
            }
        }
    }
}
