package fr.pacifista.api.server.shop.service.services.shop;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.shop.client.dtos.shop.PlayerShopItemDTO;
import fr.pacifista.api.server.shop.service.entities.shop.PlayerShopItem;
import fr.pacifista.api.server.shop.service.mappers.shop.PlayerShopItemMapper;
import fr.pacifista.api.server.shop.service.repositories.shop.PlayerShopItemRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerShopItemService extends ApiService<PlayerShopItemDTO, PlayerShopItem, PlayerShopItemMapper, PlayerShopItemRepository> {

    public PlayerShopItemService(PlayerShopItemRepository repository, PlayerShopItemMapper mapper) {
        super(repository, mapper);
    }

}
