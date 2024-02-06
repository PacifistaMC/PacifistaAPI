package fr.pacifista.api.web.shop.service.articles.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import fr.pacifista.api.web.shop.service.categories.mappers.ShopCategoryMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ShopCategoryMapper.class)
public interface ShopArticleMapper extends ApiMapper<ShopArticle, ShopArticleDTO> {
}
