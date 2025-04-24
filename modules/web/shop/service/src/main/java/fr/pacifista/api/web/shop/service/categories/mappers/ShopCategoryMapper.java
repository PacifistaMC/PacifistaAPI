package fr.pacifista.api.web.shop.service.categories.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.shop.client.categories.dtos.ShopCategoryDTO;
import fr.pacifista.api.web.shop.service.articles.mappers.ShopArticleMapper;
import fr.pacifista.api.web.shop.service.categories.entities.ShopCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ShopArticleMapper.class)
public interface ShopCategoryMapper extends ApiMapper<ShopCategory, ShopCategoryDTO> {

    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articles", ignore = true)
    ShopCategory toEntity(ShopCategoryDTO dto);

}
