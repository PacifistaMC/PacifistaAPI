package fr.pacifista.api.server.shop.service.services.admin_shop;

import com.funixproductions.core.crud.mappers.ApiMapper;
import com.funixproductions.core.crud.repositories.ApiRepository;
import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopDataWithCategoryDTO;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopCategory;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopDataWithCategory;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopCategoryRepository;
import lombok.NonNull;

abstract class AdminShopDataWithCategoryService<DTO extends AdminShopDataWithCategoryDTO, ENTITY extends AdminShopDataWithCategory, MAPPER extends ApiMapper<ENTITY, DTO>, REPOSITORY extends ApiRepository<ENTITY>> extends ApiService<DTO, ENTITY, MAPPER, REPOSITORY> {

    private final AdminShopCategoryRepository adminShopCategoryRepository;

    AdminShopDataWithCategoryService(REPOSITORY repository,
                                     MAPPER mapper,
                                     AdminShopCategoryRepository adminShopCategoryRepository) {
        super(repository, mapper);
        this.adminShopCategoryRepository = adminShopCategoryRepository;
    }

    @Override
    public void afterMapperCall(@NonNull DTO dto, @NonNull ENTITY entity) {
        if (dto.getCategory() == null) return;
        final AdminShopCategory category = this.adminShopCategoryRepository
                .findByUuid(dto.getCategory().getId().toString())
                .orElseThrow(() -> new ApiNotFoundException("Une catégorie n'a pas été trouvée pour cet item. Item id: " + dto.getId()));

        entity.setCategory(category);
    }

}
