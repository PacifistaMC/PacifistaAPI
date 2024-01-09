package fr.pacifista.api.core.client.enums.clients;

import com.funixproductions.core.crud.clients.CrudClient;
import com.funixproductions.core.crud.dtos.ApiDTO;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.exceptions.ApiForbiddenException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import java.util.List;

@Getter
public abstract class FeignImpl<DTO extends ApiDTO, FEIGN_CLIENT extends CrudClient<DTO>> implements CrudClient<DTO> {

    public static final String KUBE_INTERNAL_PATH = "/kubeinternal";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private final FEIGN_CLIENT client;

    /**
     * @param moduleName route to access service, like for example you have <a href="https://api.pacifista.fr/sanctions">https://api.pacifista.fr/sanctions</a> the arg will be sanctions
     * @param clientClass the class to create feign client for example "PacifistaSanctionClient.class"
     */
    public FeignImpl(@NonNull final String moduleName, @NonNull final Class<FEIGN_CLIENT> clientClass) {
        final String pacifistaApiDomain = System.getenv("PACIFISTA_API_URL_DOMAIN");
        final Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();

        this.client = Feign.builder()
                .requestInterceptor(new FeignTokenInterceptor())
                .contract(new SpringMvcContract())
                .client(new OkHttpClient())
                .decoder(new GsonDecoder(gson))
                .encoder(new GsonEncoder(gson))
                .target(clientClass, String.format("%s/%s", Strings.isBlank(pacifistaApiDomain) ? "https://api.pacifista.fr" : pacifistaApiDomain, moduleName));
    }

    @Override
    public PageDTO<DTO> getAll(@NotBlank String page,
                               @NotBlank String elemsPerPage,
                               @NotBlank String search,
                               @NotBlank String sort) throws ApiException {
        try {
            return client.getAll(page, elemsPerPage, search, sort);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public DTO findById(@NotBlank String id) throws ApiException {
        try {
            return client.findById(id);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public DTO create(DTO request) throws ApiException {
        try {
            return client.create(request);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public List<DTO> create(List<DTO> request) throws ApiException {
        try {
            return client.create(request);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public DTO update(DTO request) throws ApiException {
        try {
            return client.update(request);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public List<DTO> update(List<DTO> request) throws ApiException {
        try {
            return client.update(request);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public void delete(String id) throws ApiException {
        try {
            client.delete(id);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public DTO updatePut(@Valid DTO request) {
        try {
            return client.updatePut(request);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public List<DTO> updatePut(@Valid List<@Valid DTO> request) {
        try {
            return client.updatePut(request);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    @Override
    public void delete(String... ids) throws ApiException {
        try {
            client.delete(ids);
        } catch (FeignException e) {
            throw handleFeignException(e);
        }
    }

    public ApiException handleFeignException(FeignException e) throws ApiException {
        final int httpCode = e.status();

        switch (httpCode) {
            case 400 -> throw new ApiBadRequestException("400 Bad request: " + e.getMessage(), e);
            case 401 -> throw new ApiForbiddenException("401 Vous n'êtes pas connecté à l'api.", e);
            case 403 -> throw new ApiForbiddenException("403 Vous n'avez pas la permission.", e);
            case 404 -> throw new ApiNotFoundException("404 Not found: " + e.getMessage(), e);
            default -> throw new ApiException(httpCode + ' ' + e.getMessage(), e);
        }
    }
}
