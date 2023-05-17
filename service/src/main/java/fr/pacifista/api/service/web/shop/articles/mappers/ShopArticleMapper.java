package fr.pacifista.api.service.web.shop.articles.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.web.shop.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.service.web.shop.articles.entities.ShopArticle;
import fr.pacifista.api.service.web.shop.categories.mappers.ShopCategoryMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ShopCategoryMapper.class)
public interface ShopArticleMapper extends ApiMapper<ShopArticle, ShopArticleDTO> {
}
