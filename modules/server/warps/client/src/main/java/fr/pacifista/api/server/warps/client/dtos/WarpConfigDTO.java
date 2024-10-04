package fr.pacifista.api.server.warps.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarpConfigDTO extends ApiDTO {

    /**
     * Le warp en question
     */
    @NotNull
    private WarpDTO warp;

    /**
     * Si le warp est visible dans les menus
     */
    @NotNull
    private Boolean isVisibleInMenu;

    /**
     * Si l'accès au warp est public ou non, si l'accès est désactivé, uniquement le staff et le créateur du warp peuvent l'utiliser.
     */
    @NotNull
    private Boolean publicAccess;

    /**
     * Si le warp est gratuit
     */
    @NotNull
    private Boolean isFreeToUse;

    /**
     * Si le warp est payant, le prix d'utilisation
     */
    @NotNull
    private Double price;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final WarpConfigDTO other) {
            return warp.equals(other.warp) &&
                    isVisibleInMenu.equals(other.isVisibleInMenu) &&
                    publicAccess.equals(other.publicAccess) &&
                    isFreeToUse.equals(other.isFreeToUse) &&
                    price.equals(other.price) &&
                    super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return warp.hashCode() +
                isVisibleInMenu.hashCode() +
                publicAccess.hashCode() +
                isFreeToUse.hashCode() +
                price.hashCode() +
                super.hashCode();
    }

    public static WarpConfigDTO initWithDefaults(WarpDTO warp) {
        return new WarpConfigDTO(
                warp,
                true,
                true,
                true,
                0.0
        );
    }

}
