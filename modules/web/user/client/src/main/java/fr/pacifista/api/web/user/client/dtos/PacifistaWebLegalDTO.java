package fr.pacifista.api.web.user.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.web.user.client.enums.PacifistaLegalDocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacifistaWebLegalDTO extends ApiDTO {

    @NotBlank(message = "Le contenu du document légal ne doit pas être vide.")
    private String contentHtml;

    @NotNull(message = "Le type du document légal ne doit pas être vide.")
    private PacifistaLegalDocumentType type;

    @Nullable
    private String editReason;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final PacifistaWebLegalDTO other) {
            return super.equals(other)
                    && contentHtml.equals(other.contentHtml)
                    && type.equals(other.type);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() +
                contentHtml.hashCode() +
                type.hashCode();
    }
}
