package fr.pacifista.api.web.shop.client.categories.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopCategoryDTO extends ApiDTO {

    @NotBlank(message = "Le nom de la catégorie ne peut pas être vide")
    @Size(max = 20, min = 1, message = "Le nom de la catégorie ne peut pas dépasser 20 caractères et doit contenir au moins 1 caractère")
    private String name;

    @Size(max = 100, min = 10, message = "La description de la catégorie ne peut pas dépasser 100 caractères et doit contenir au moins 10 caractères")
    @NotBlank(message = "La description de la catégorie ne peut pas être vide")
    private String description;

    @NotNull(message = "Le choix de multi-achat ne peut pas être nul")
    private Boolean multiPurchaseAllowed;

    private Set<ShopArticleDTO> articles;

    public ShopCategoryDTO(final String name,
                           final String description,
                           final Boolean multiPurchaseAllowed) {
        this.name = name;
        this.description = description;
        this.multiPurchaseAllowed = multiPurchaseAllowed;
    }

}
