package fr.pacifista.api.web.shop.service.categories.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.web.shop.service.categories.entities.ShopCategory;
import fr.pacifista.api.web.shop.service.categories.mappers.ShopCategoryMapper;
import fr.pacifista.api.web.shop.service.categories.repositories.ShopCategoryRepository;
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
