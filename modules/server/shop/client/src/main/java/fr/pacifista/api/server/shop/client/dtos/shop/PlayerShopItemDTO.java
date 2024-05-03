package fr.pacifista.api.server.shop.client.dtos.shop;

import fr.pacifista.api.core.client.dtos.MinecraftPlayerDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PlayerShopItemDTO extends MinecraftPlayerDTO {

    @NotBlank(message = "L'item est obligatoire")
    private String itemSerialized;

    @NotBlank(message = "Le prix est obligatoire")
    @Min(value = 1, message = "Le prix doit être supérieur à 0")
    private Double price;

    private Date soldAt;

    public PlayerShopItemDTO(String itemSerialized, Double price) {
        this.itemSerialized = itemSerialized;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final PlayerShopItemDTO other) {
            boolean equals = super.equals(obj) &&
                    (itemSerialized != null && itemSerialized.equals(other.itemSerialized)) &&
                    (price != null && price.equals(other.price));

            if (soldAt != null) {
                return equals && soldAt.equals(other.soldAt);
            } else {
                return equals && other.soldAt == null;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 13 +
            (itemSerialized != null ? itemSerialized.hashCode() : 0) +
            (price != null ? price.hashCode() : 0) +
            (soldAt != null ? soldAt.hashCode() : 0);
    }
}
