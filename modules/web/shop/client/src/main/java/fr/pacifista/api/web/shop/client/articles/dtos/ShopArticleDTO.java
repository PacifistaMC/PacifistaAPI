package fr.pacifista.api.web.shop.client.articles.dtos;

import com.funixproductions.core.files.dtos.ApiStorageFileDTO;
import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class ShopArticleDTO extends ApiStorageFileDTO {

    @NotNull(message = "La catégorie ne peut pas être nulle")
    private ShopCategoryDTO category;

    @NotBlank(message = "Le nom ne peut pas être vide")
    @Size(min = 3, max = 50, message = "Le nom doit contenir entre 3 et 50 caractères")
    private String name;

    @NotBlank(message = "La description ne peut pas être vide")
    @Size(min = 3, max = 255, message = "La description doit contenir entre 3 et 255 caractères")
    private String description;

    @NotBlank(message = "La description HTML ne peut pas être vide")
    private String htmlDescription;

    @NotNull(message = "Le prix ne peut pas être nul")
    @Min(value = 0, message = "Le prix ne peut pas être négatif")
    private Double price;

    @NotBlank(message = "La commande exécutée ne peut pas être vide")
    @Size(min = 3, max = 255, message = "La commande exécutée doit contenir entre 3 et 255 caractères")
    private String commandExecuted;

    /**
     * Si le type de serveur est null c'est que la commande sera exécutée sur le proxy
     */
    @Nullable
    private ServerType serverType;

    private Double tax;

    private Double priceWithTax;

    public void setTax(Double tax) {
        this.tax = this.formatPrice(tax);
    }

    public void setPriceWithTax(Double priceWithTax) {
        this.priceWithTax = this.formatPrice(priceWithTax);
    }

    private Double formatPrice(Double value) {
        if (value == null) {
            return null;
        }

        final BigDecimal bd = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
