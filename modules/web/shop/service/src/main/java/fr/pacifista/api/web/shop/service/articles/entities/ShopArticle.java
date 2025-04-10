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

    @Column(nullable = false, name = "html_description", length = 100000)
    private String htmlDescription;

    @Column(nullable = false, name = "markdown_description", length = 100000)
    private String markDownDescription;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final ShopArticle article) {
            return article.getCategory().equals(this.category) &&
                    article.getName().equals(this.name) &&
                    article.getDescription().equals(this.description) &&
                    article.getHtmlDescription().equals(this.htmlDescription) &&
                    article.getMarkDownDescription().equals(this.markDownDescription) &&
                    article.getPrice().equals(this.price) &&
                    article.getCommandExecuted().equals(this.commandExecuted) &&
                    article.getServerType() == this.serverType &&
                    super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.category.hashCode() +
                this.name.hashCode() +
                this.description.hashCode() +
                this.htmlDescription.hashCode() +
                this.markDownDescription.hashCode() +
                this.price.hashCode() +
                this.commandExecuted.hashCode() +
                (this.serverType != null ? this.serverType.hashCode() : 0) +
                super.hashCode();
    }
}
