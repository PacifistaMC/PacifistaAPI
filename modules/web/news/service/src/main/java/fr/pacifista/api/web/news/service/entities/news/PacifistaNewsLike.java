package fr.pacifista.api.web.news.service.entities.news;

import fr.pacifista.api.web.news.service.entities.PacifistaNewsUserData;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pacifista_news_likes")
public class PacifistaNewsLike extends PacifistaNewsUserData {
}
