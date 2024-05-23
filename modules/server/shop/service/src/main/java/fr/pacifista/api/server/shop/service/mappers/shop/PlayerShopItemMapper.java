package fr.pacifista.api.server.shop.service.mappers.shop;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.shop.client.dtos.shop.PlayerShopItemDTO;
import fr.pacifista.api.server.shop.service.entities.shop.PlayerShopItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerShopItemMapper extends ApiMapper<PlayerShopItem, PlayerShopItemDTO> {
}
