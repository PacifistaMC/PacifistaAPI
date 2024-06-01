package fr.pacifista.api.server.shop.service.resources.admin_shop;

import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopCategoryDTO;
import fr.pacifista.api.server.shop.service.services.admin_shop.AdminShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.UUID;

@SpringBootTest
abstract class AdminShopDataWithCategoryTest {

    @Autowired
    private AdminShopCategoryService adminShopCategoryService;

    protected AdminShopCategoryDTO generateCategory() {
        return adminShopCategoryService.create(new AdminShopCategoryDTO(UUID.randomUUID().toString(), new Random().nextDouble(1000), UUID.randomUUID().toString()));
    }
}
