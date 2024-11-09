package fr.pacifista.api.web.news.service.entities.comments;

import fr.pacifista.api.web.news.service.entities.PacifistaNewsUserData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "parent")
    private List<PacifistaNewsComment> answers;

    public List<PacifistaNewsComment> getAnswers() {
        if (this.parent == null && this.answers != null) {
            for (final PacifistaNewsComment comment : this.answers) {
                comment.setParent(null);
            }
            return this.answers;
        } else {
            return null;
        }
    }
}
