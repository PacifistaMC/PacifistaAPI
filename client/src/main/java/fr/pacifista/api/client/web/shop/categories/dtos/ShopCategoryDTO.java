package fr.pacifista.api.client.web.shop.categories.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopCategoryDTO extends ApiDTO {

    @NotBlank
    private String name;

}
