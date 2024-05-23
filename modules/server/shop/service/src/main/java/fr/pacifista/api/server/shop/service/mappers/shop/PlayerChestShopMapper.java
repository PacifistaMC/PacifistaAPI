package fr.pacifista.api.server.shop.service.mappers.shop;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.shop.client.dtos.shop.PlayerChestShopDTO;
import fr.pacifista.api.server.shop.service.entities.shop.PlayerChestShop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerChestShopMapper extends ApiMapper<PlayerChestShop, PlayerChestShopDTO> {
}
