package fr.pacifista.api.server.shop.service.services.shop;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.shop.client.dtos.shop.PlayerChestShopDTO;
import fr.pacifista.api.server.shop.service.entities.shop.PlayerChestShop;
import fr.pacifista.api.server.shop.service.mappers.shop.PlayerChestShopMapper;
import fr.pacifista.api.server.shop.service.repositories.shop.PlayerChestShopRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerChestShopService extends ApiService<PlayerChestShopDTO, PlayerChestShop, PlayerChestShopMapper, PlayerChestShopRepository> {

    public PlayerChestShopService(PlayerChestShopRepository repository, PlayerChestShopMapper mapper) {
        super(repository, mapper);
    }

}
