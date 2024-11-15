package fr.pacifista.api.web.news.service.entities.comments;

import fr.pacifista.api.web.news.service.entities.PacifistaNewsUserData;
import jakarta.persistence.Column;
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
@Entity(name = "pacifista_news_comments")
public class PacifistaNewsComment extends PacifistaNewsUserData {

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private PacifistaNewsComment parent;

    @Column(name = "content", length = 1500, nullable = false)
    private String content;

    @Column(name = "likes", nullable = false)
    private Integer likes;

}
