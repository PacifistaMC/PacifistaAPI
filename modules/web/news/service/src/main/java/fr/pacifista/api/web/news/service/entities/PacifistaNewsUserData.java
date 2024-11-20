package fr.pacifista.api.web.news.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class PacifistaNewsUserData extends ApiEntity {

    @ManyToOne
    @JoinColumn(name = "news_id", nullable = false)
    private PacifistaNews news;

    @Column(name = "minecraft_username", nullable = false)
    private String minecraftUsername;

    @Column(name = "funixprod_user_id", nullable = false)
    private String funixProdUserId;

    public UUID getFunixProdUserId() {
        if (funixProdUserId == null) {
            return null;
        } else {
            return UUID.fromString(funixProdUserId);
        }
    }

    public void setFunixProdUserId(UUID funixProdUserId) {
        if (funixProdUserId == null) {
            this.funixProdUserId = null;
        } else {
            this.funixProdUserId = funixProdUserId.toString();
        }
    }
}
