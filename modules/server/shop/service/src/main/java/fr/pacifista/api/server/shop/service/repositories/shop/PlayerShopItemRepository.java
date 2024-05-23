package fr.pacifista.api.server.shop.service.repositories.shop;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.shop.service.entities.shop.PlayerShopItem;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerShopItemRepository extends ApiRepository<PlayerShopItem> {
}
