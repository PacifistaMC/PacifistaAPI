package fr.pacifista.api.server.shop.client.dtos.admin_shop;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminShopItemDTO extends AdminShopDataWithCategoryDTO {

    /**
     * Material de l'item
     */
    @NotBlank(message = "Material is mandatory")
    private String material;

    /**
     * Prix de l'item
     */
    @NotNull(message = "Price is mandatory")
    @Min(value = 0, message = "Price must be greater than 0")
    private Double price;

    public AdminShopItemDTO(String material, Double price, AdminShopCategoryDTO category) {
        this.material = material;
        this.price = price;
        this.setCategory(category);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final AdminShopItemDTO other) {
            return super.equals(obj) &&
                    (material != null && other.material != null && material.equals(other.material)) &&
                    (price != null && other.price != null && price.equals(other.price));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 13 +
            (material != null ? material.hashCode() : 0) +
            (price != null ? price.hashCode() : 0);
    }
}
