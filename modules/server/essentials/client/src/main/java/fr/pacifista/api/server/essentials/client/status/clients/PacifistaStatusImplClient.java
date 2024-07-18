package fr.pacifista.api.server.essentials.client.status.clients;

import com.google.gson.Gson;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.server.essentials.client.status.dtos.PacifistaServerInfoDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

public class PacifistaStatusImplClient implements PacifistaStatus {

    public static final String PATH = "essentials/status";

    private final PacifistaStatusClient pacifistaStatusClient;

    public PacifistaStatusImplClient() {
        final String pacifistaApiDomain = System.getenv("PACIFISTA_API_URL_DOMAIN");
        final Gson gson = new Gson();

        this.pacifistaStatusClient = Feign.builder()
                .requestInterceptor(new FeignTokenInterceptor())
                .contract(new SpringMvcContract())
                .client(new OkHttpClient())
                .decoder(new GsonDecoder(gson))
                .encoder(new GsonEncoder(gson))
                .target(
                        PacifistaStatusClient.class, String.format("%s/%s",
                                Strings.isBlank(pacifistaApiDomain) ? "https://api.pacifista.fr" : pacifistaApiDomain,
                                PATH)
                );
    }

    @Override
    public PacifistaServerInfoDTO getServerInfo() {
        try {
            return pacifistaStatusClient.getServerInfo();
        } catch (FeignException e) {
            throw FeignImpl.handleFeignException(e);
        }
    }
}
