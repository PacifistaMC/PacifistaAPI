package fr.pacifista.api.web.shop.client.categories.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopCategoryDTO extends ApiDTO {

    @NotBlank(message = "Le nom de la catégorie ne peut pas être vide")
    private String name;

    @NotBlank(message = "La description de la catégorie ne peut pas être vide")
    private String description;

}
