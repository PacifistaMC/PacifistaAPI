package fr.pacifista.api.web.news.client.dtos.comments;

import fr.pacifista.api.web.news.client.dtos.PacifistaNewsUserDataDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
     * Réponses à ce commentaire
     */
    private List<PacifistaNewsCommentDTO> answers;

}
