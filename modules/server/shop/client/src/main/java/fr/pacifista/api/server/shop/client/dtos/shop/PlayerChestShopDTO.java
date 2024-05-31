package fr.pacifista.api.server.shop.client.dtos.shop;

import fr.pacifista.api.core.client.dtos.LocationDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerChestShopDTO extends LocationDTO {

    /**
     * Le propriétaire du shop
     */
    @NotNull(message = "Le joueur id est obligatoire")
    private UUID playerId;

    /**
     * L'item qui est vendu dans le shop, si null c'est que le shop est encore en phase de création
     */
    @Nullable
    private String itemSerialized;

    /**
     * Si le prix d'achat est à 0, le shop ne sera pas un shop d'achat
     */
    @NotNull(message = "Le prix d'achat est obligatoire")
    @Min(value = 0, message = "Le prix d'achat doit être supérieur ou égal à 0")
    private Double priceBuy;

    /**
     * Si le prix de vente est à 0, le shop ne sera pas un shop de vente
     */
    @NotNull(message = "Le prix de vente est obligatoire")
    @Min(value = 0, message = "Le prix de vente doit être supérieur ou égal à 0")
    private Double priceSell;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final PlayerChestShopDTO other) {
            return super.equals(obj) &&
                    (itemSerialized != null && itemSerialized.equals(other.itemSerialized)) &&
                    (priceBuy != null && priceBuy.equals(other.priceBuy)) &&
                    (priceSell != null && priceSell.equals(other.priceSell)) &&
                    (playerId != null && playerId.equals(other.playerId));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 13 +
            (itemSerialized != null ? itemSerialized.hashCode() : 0) +
            (priceSell != null ? priceSell.hashCode() : 0) +
            (priceBuy != null ? priceBuy.hashCode() : 0) +
            (playerId != null ? playerId.hashCode() : 0);
    }

}
