package fr.pacifista.api.web.shop.client.articles.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ShopArticleDTO extends ApiDTO {

    @NonNull
    private ShopCategoryDTO category;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String htmlDescription;

    @NotBlank
    private String logoUrl;

    @NonNull
    private Double price;

}
