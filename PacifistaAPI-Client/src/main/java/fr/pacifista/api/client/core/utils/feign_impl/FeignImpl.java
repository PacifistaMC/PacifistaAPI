package fr.pacifista.api.client.core.utils.feign_impl;

import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import fr.funixgaming.api.core.crud.dtos.PageDTO;
import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.funixgaming.api.core.exceptions.ApiException;
import fr.funixgaming.api.core.exceptions.ApiForbiddenException;
import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public abstract class FeignImpl<DTO extends ApiDTO, FEIGN_CLIENT extends CrudClient<DTO>> implements CrudClient<DTO> {

    private final FEIGN_CLIENT client;

    /**
     * @param moduleName route to access service, like for example you have https://api.pacifista.fr/sanctions the arg will be sanctions
     * @param clientClass the class to create feign client for example "PacifistaSanctionClient.class"
     */
    public FeignImpl(@NonNull final String moduleName, @NonNull final Class<FEIGN_CLIENT> clientClass) {
        final PacifistaApiConfig config = PacifistaApiConfig.getInstance();

        this.client = Feign.builder()
                .requestInterceptor(new FeignInterceptor(config))
                .contract(new SpringMvcContract())
                .client(new OkHttpClient())
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(clientClass, String.format("%s/%s", config.getUrlDomainPacifistaApi(), moduleName));
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
            case 400 -> throw new ApiBadRequestException("400 Bad request: " + e.contentUTF8(), e);
            case 401 -> throw new ApiForbiddenException("401 Vous n'êtes pas connecté à l'api.", e);
            case 403 -> throw new ApiForbiddenException("403 Vous n'avez pas la permission.", e);
            case 404 -> throw new ApiNotFoundException("404 Not found: " + e.contentUTF8(), e);
            default -> throw new ApiException(httpCode + ' ' + e.getMessage(), e);
        }
    }
}
