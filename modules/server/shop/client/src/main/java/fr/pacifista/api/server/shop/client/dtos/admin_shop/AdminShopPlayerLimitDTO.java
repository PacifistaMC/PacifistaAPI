package fr.pacifista.api.server.shop.client.dtos.admin_shop;

import jakarta.validation.constraints.Min;
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
public class AdminShopPlayerLimitDTO extends AdminShopDataWithCategoryDTO {

    @NotNull(message = "L'identifiant du joueur est obligatoire")
    private UUID playerId;

    @NotNull(message = "L'argent généré est obligatoire")
    @Min(value = 0, message = "L'argent généré doit être supérieur ou égal à 0")
    private Double moneyGenerated;

    public AdminShopPlayerLimitDTO(UUID playerId, Double moneyGenerated, AdminShopCategoryDTO category) {
        this.playerId = playerId;
        this.moneyGenerated = moneyGenerated;
        this.setCategory(category);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final AdminShopPlayerLimitDTO other) {
            return super.equals(obj) &&
                    (playerId != null && other.playerId != null && playerId.equals(other.playerId)) &&
                    (moneyGenerated != null && other.moneyGenerated != null && moneyGenerated.equals(other.moneyGenerated));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 13 +
            (playerId != null ? playerId.hashCode() : 0) +
            (moneyGenerated != null ? moneyGenerated.hashCode() : 0);
    }
}
