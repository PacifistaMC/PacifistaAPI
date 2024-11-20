package fr.pacifista.api.web.news.service.entities.news;

import fr.pacifista.api.web.news.service.entities.PacifistaNewsUserData;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_news_likes")
public class PacifistaNewsLike extends PacifistaNewsUserData {
}
