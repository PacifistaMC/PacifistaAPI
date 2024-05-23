package fr.pacifista.api.server.shop.service.entities.admin_shop;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AdminShopDataWithCategory extends ApiEntity {

    @ManyToOne
    @JoinColumn(nullable = false, name = "admin_shop_category_id")
    private AdminShopCategory category;

}
