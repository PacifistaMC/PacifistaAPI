package fr.pacifista.api.server.shop.service.mappers.admin_shop;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.shop.client.dtos.admin_shop.AdminShopPlayerLimitDTO;
import fr.pacifista.api.server.shop.service.entities.admin_shop.AdminShopPlayerLimit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AdminShopCategoryMapper.class)
public interface AdminShopPlayerLimitMapper extends ApiMapper<AdminShopPlayerLimit, AdminShopPlayerLimitDTO> {
}
