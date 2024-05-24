package fr.pacifista.api.web.user.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.web.user.client.enums.PacifistaLegalDocumentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "PacifistaWebLegal")
public class PacifistaWebLegal extends ApiEntity {

    @Column(nullable = false, name = "content_html")
    private String contentHtml;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type")
    private PacifistaLegalDocumentType type;

    @Column(name = "edit_reason")
    private String editReason;

}
