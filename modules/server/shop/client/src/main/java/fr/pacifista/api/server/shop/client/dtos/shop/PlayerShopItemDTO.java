package fr.pacifista.api.server.shop.client.dtos.shop;

import fr.pacifista.api.core.client.dtos.MinecraftPlayerDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PlayerShopItemDTO extends MinecraftPlayerDTO {

    @NotBlank(message = "L'item est obligatoire")
    private String itemSerialized;

    @NotNull(message = "Le prix est obligatoire")
    @Min(value = 1, message = "Le prix doit être supérieur à 0")
    private Double price;

    private String buyerName;

    private Date soldAt;

    public PlayerShopItemDTO(UUID playerId,
                             String playerName,
                             String itemSerialized,
                             Double price) {
        super.setMinecraftUsername(playerName);
        super.setMinecraftUuid(playerId);
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
