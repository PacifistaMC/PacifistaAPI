package fr.pacifista.api.server.shop.service.mappers.admin_shop;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopCategoryDTO;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminShopCategoryMapper extends ApiMapper<AdminShopCategory, AdminShopCategoryDTO> {
}
