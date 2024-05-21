package fr.pacifista.api.server.shop.service.services.admin_shop;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.google.common.base.Strings;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopCategoryDTO;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopCategory;
import fr.pacifista.api.server.shop.service.mappers.admin_shop.AdminShopCategoryMapper;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopCategoryRepository;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopItemRepository;
import fr.pacifista.api.server.shop.service.repositories.admin_shop.AdminShopPlayerLimitRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AdminShopCategoryService extends ApiService<AdminShopCategoryDTO, AdminShopCategory, AdminShopCategoryMapper, AdminShopCategoryRepository> {

    private final AdminShopItemRepository adminShopItemRepository;
    private final AdminShopPlayerLimitRepository adminShopPlayerLimitRepository;

    public AdminShopCategoryService(AdminShopCategoryRepository repository,
                                    AdminShopCategoryMapper mapper,
                                    AdminShopItemRepository adminShopItemRepository,
                                    AdminShopPlayerLimitRepository adminShopPlayerLimitRepository) {
        super(repository, mapper);
        this.adminShopItemRepository = adminShopItemRepository;
        this.adminShopPlayerLimitRepository = adminShopPlayerLimitRepository;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<AdminShopCategory> entity) {
        for (AdminShopCategory category : entity) {
            if (category.getId() == null && !Strings.isNullOrEmpty(category.getName()) && super.getRepository().existsByNameIgnoreCase(category.getName())) {
                throw new ApiBadRequestException("La catégorie " + category.getName() + " existe déjà.");
            }
        }
    }

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<AdminShopCategory> entity) {
        this.adminShopItemRepository.deleteAllByCategoryIn(entity);
        this.adminShopPlayerLimitRepository.deleteAllByCategoryIn(entity);
    }
}
