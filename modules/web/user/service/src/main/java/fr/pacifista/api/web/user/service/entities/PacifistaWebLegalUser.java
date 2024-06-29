package fr.pacifista.api.web.user.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.web.user.client.enums.PacifistaLegalDocumentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "pacifista_web_legals_users")
public class PacifistaWebLegalUser extends ApiEntity {

    /**
     * User Id that have accepted the legal document
     */
    @Column(nullable = false, name = "user_id")
    private String userId;

    /**
     * Legal document type
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type")
    private PacifistaLegalDocumentType type;

    public UUID getUserId() {
        if (userId == null) {
            return null;
        } else {
            return UUID.fromString(userId);
        }
    }

    public void setUserId(UUID userId) {
        if (userId == null) {
            this.userId = null;
        } else {
            this.userId = userId.toString();
        }
    }
}
