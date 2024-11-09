package fr.pacifista.api.web.news.client.dtos.comments;

import fr.pacifista.api.web.news.client.dtos.PacifistaNewsUserDataDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String content;

    /**
     * Nombre de likes sur ce commentaire
     */
    @NotNull
    private Integer likes;

    /**
     * Réponses à ce commentaire
     */
    @NotNull
    private List<PacifistaNewsCommentDTO> answers;

}
