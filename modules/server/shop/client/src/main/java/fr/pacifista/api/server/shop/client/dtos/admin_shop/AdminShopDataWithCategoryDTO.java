package fr.pacifista.api.server.shop.client.dtos.admin_shop;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AdminShopDataWithCategoryDTO extends ApiDTO {

    /**
     * Catégorie de l'item
     */
    @NotNull(message = "Category is mandatory")
    private AdminShopCategoryDTO category;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final AdminShopDataWithCategoryDTO other) {
            return super.equals(obj) &&
                    (category != null && other.category != null && category.equals(other.category));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 13 +
            (category != null ? category.hashCode() : 0);
    }
}
