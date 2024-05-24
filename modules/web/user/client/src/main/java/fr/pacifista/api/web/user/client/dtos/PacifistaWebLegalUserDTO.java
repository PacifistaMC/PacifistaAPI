package fr.pacifista.api.web.user.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.web.user.client.enums.PacifistaLegalDocumentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacifistaWebLegalUserDTO extends ApiDTO {

    /**
     * User Id that have accepted the legal document
     */
    @NotNull(message = "L'identifiant de l'utilisateur ne doit pas être vide.")
    private UUID userId;

    /**
     * Legal document type
     */
    @NotNull(message = "Le type du document légal ne doit pas être vide.")
    private PacifistaLegalDocumentType type;


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final PacifistaWebLegalUserDTO other) {
            return super.equals(other)
                    && userId.equals(other.userId)
                    && type.equals(other.type);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() +
                userId.hashCode() +
                type.hashCode();
    }
}
