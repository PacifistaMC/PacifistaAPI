package fr.pacifista.api.web.shop.service.articles.entities;

import com.funixproductions.core.files.entities.ApiStorageFile;
import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.web.shop.service.categories.entities.ShopCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "shop_articles")
public class ShopArticle extends ApiStorageFile {

    @ManyToOne
    @JoinColumn(nullable = false, name = "category_id")
    private ShopCategory category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "html_description", length = 10000)
    private String htmlDescription;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, name = "command_executed")
    private String commandExecuted;

    @Column(name = "server_type")
    @Enumerated(EnumType.STRING)
    private ServerType serverType;

    public Double getPrice() {
        return ShopArticleDTO.formatPrice(price);
    }

    public void setPrice(Double price) {
        this.price = ShopArticleDTO.formatPrice(price);
    }
}
