package fr.pacifista.api.server.shop.client.dtos.shop;

import fr.pacifista.api.core.client.dtos.LocationDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerChestShopDTO extends LocationDTO {

    @NotNull(message = "Le joueur id est obligatoire")
    private UUID playerId;

    @NotBlank(message = "L'item est obligatoire")
    private String itemSerialized;

    @NotNull(message = "Le prix est obligatoire")
    @Min(value = 1, message = "Le prix doit être supérieur à 0")
    private Double price;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final PlayerChestShopDTO other) {
            return super.equals(obj) &&
                    (itemSerialized != null && itemSerialized.equals(other.itemSerialized)) &&
                    (price != null && price.equals(other.price)) &&
                    (playerId != null && playerId.equals(other.playerId));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 13 +
            (itemSerialized != null ? itemSerialized.hashCode() : 0) +
            (price != null ? price.hashCode() : 0) +
            (playerId != null ? playerId.hashCode() : 0);
    }

}
