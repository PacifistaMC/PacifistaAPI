package fr.pacifista.api.service.web.shop.categories.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.funixgaming.api.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.client.web.shop.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.service.web.shop.categories.entities.ShopCategory;
import fr.pacifista.api.service.web.shop.categories.mappers.ShopCategoryMapper;
import fr.pacifista.api.service.web.shop.categories.repositories.ShopCategoryRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ShopCategoryService extends ApiService<ShopCategoryDTO, ShopCategory, ShopCategoryMapper, ShopCategoryRepository> {

    public ShopCategoryService(ShopCategoryRepository repository,
                               ShopCategoryMapper mapper) {
        super(repository, mapper);
    }

    public ShopCategory findById(@Nullable final UUID publicId) {
        if (publicId == null) {
            throw new ApiBadRequestException("La categorie id est requise.");
        }

        final Optional<ShopCategory> search = this.getRepository().findByUuid(publicId.toString());
        if (search.isPresent()) {
            return search.get();
        } else {
            throw new ApiNotFoundException("La categorie n'existe pas.");
        }
    }

}
