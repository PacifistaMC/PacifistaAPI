package fr.pacifista.api.web.news.service.entities.comments;

import fr.pacifista.api.web.news.service.entities.PacifistaNewsUserData;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pacifista_news_comments_likes")
public class PacifistaNewsCommentLike extends PacifistaNewsUserData {

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false, updatable = false)
    private PacifistaNewsComment comment;

}
