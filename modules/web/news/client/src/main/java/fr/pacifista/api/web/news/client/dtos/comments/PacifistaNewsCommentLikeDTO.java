package fr.pacifista.api.web.news.client.dtos.comments;

import fr.pacifista.api.web.news.client.dtos.PacifistaNewsUserDataDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacifistaNewsCommentLikeDTO extends PacifistaNewsUserDataDTO {

    /**
     * Le commentaire de la news qui a été liké
     */
    @NotBlank
    private PacifistaNewsCommentDTO comment;

}
