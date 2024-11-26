package fr.pacifista.api.web.news.client.dtos.comments;

import fr.pacifista.api.web.news.client.dtos.PacifistaNewsUserDataDTO;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacifistaNewsCommentDTO extends PacifistaNewsUserDataDTO {

    /**
     * Commentaire parent
     */
    @Nullable
    private PacifistaNewsCommentDTO parent;

    /**
     * Contenu du commentaire en texte plein, pas de HTML
     */
    @NotBlank(message = "Le commentaire ne doit pas être vide")
    @Size(message = "Le commentaire doit contenir entre 2 et 1500 caractrères.", min = 2, max = 1500)
    private String content;

    /**
     * Nombre de likes sur ce commentaire
     */
    private Integer likes;

    /**
     * Indique si l'utilisateur a aimé ce commentaire, false si pas de bearer
     */
    private Boolean liked;

    public PacifistaNewsCommentDTO(final String content, final PacifistaNewsDTO newsDTO) {
        this.content = content;
        super.setNews(newsDTO);
    }

    public PacifistaNewsCommentDTO(final @Nullable PacifistaNewsCommentDTO parent,
                                   final String content,
                                   final PacifistaNewsDTO newsDTO) {
        this.parent = parent;
        this.content = content;
        super.setNews(newsDTO);
    }

}
