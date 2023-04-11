package fr.pacifista.api.service.web.shop.categories.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.web.shop.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.service.web.shop.categories.entities.ShopCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopCategoryMapper extends ApiMapper<ShopCategory, ShopCategoryDTO> {
}
