package fr.pacifista.api.server.shop.client.dtos.admin_shop;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminShopCategoryDTO extends ApiDTO {

    /**
     * Nom de la catégorie
     */
    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    private String name;

    /**
     * Limite de vente de la catégorie, permet de savoir si un joueur peut vendre des items de cette catégorie (limité ou pas)
     */
    @NotNull(message = "La limite de vente de la catégorie est obligatoire")
    @Min(value = 0, message = "La limite de vente de la catégorie doit être supérieure ou égale à 0")
    private Double moneySellLimit;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final AdminShopCategoryDTO other) {
            return super.equals(obj) &&
                    (name != null && other.name != null && name.equals(other.name)) &&
                    (moneySellLimit != null && other.moneySellLimit != null && moneySellLimit.equals(other.moneySellLimit));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 13 +
            (name != null ? name.hashCode() : 0) +
                (moneySellLimit != null ? moneySellLimit.hashCode() : 0);
    }
}
