package fr.pacifista.api.web.shop.service.articles.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import fr.pacifista.api.web.shop.service.categories.mappers.ShopCategoryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ShopCategoryMapper.class)
public interface ShopArticleMapper extends ApiMapper<ShopArticle, ShopArticleDTO> {
    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "tax", ignore = true)
    @Mapping(target = "priceWithTax", ignore = true)
    @Mapping(target = "category.articles", ignore = true)
    @Mapping(target = "category.id", source = "category.uuid")
    ShopArticleDTO toDto(ShopArticle entity);
}
