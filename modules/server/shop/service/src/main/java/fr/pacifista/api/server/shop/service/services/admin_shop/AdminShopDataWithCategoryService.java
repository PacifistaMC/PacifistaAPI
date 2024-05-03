package fr.pacifista.api.server.shop.service.services.admin_shop;

import com.funixproductions.core.crud.mappers.ApiMapper;
import com.funixproductions.core.crud.repositories.ApiRepository;
import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopDataWithCategoryDTO;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopCategory;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopDataWithCategory;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopCategoryRepository;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AdminShopDataWithCategoryService<DTO extends AdminShopDataWithCategoryDTO, ENTITY extends AdminShopDataWithCategory, MAPPER extends ApiMapper<ENTITY, DTO>, REPOSITORY extends ApiRepository<ENTITY>> extends ApiService<DTO, ENTITY, MAPPER, REPOSITORY> {

    private final AdminShopCategoryRepository adminShopCategoryRepository;

    AdminShopDataWithCategoryService(REPOSITORY repository,
                                     MAPPER mapper,
                                     AdminShopCategoryRepository adminShopCategoryRepository) {
        super(repository, mapper);
        this.adminShopCategoryRepository = adminShopCategoryRepository;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<ENTITY> entity) {
        final List<String> categoriesIds = new ArrayList<>();
        for (ENTITY entityItem : entity) {
            if (entityItem.getCategory() != null) {
                categoriesIds.add(entityItem.getCategory().getId().toString());
            }
        }
        final Iterable<AdminShopCategory> categories = this.adminShopCategoryRepository.findAllByUuidIn(categoriesIds);

        for (final ENTITY entityItem : entity) {
            if (entityItem.getCategory() != null) {
                for (final AdminShopCategory category : categories) {
                    if (entityItem.getCategory().getId().equals(category.getId())) {
                        entityItem.setCategory(category);
                        break;
                    }
                }
            }
        }
    }
}
