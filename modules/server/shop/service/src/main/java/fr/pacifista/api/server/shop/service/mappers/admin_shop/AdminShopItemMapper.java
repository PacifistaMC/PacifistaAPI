package fr.pacifista.api.server.shop.service.mappers.admin_shop;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopItemDTO;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AdminShopCategoryMapper.class)
public interface AdminShopItemMapper extends ApiMapper<AdminShopItem, AdminShopItemDTO> {
}
