package fr.pacifista.api.support.tickets.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.support.tickets.client.enums.TicketCreationSource;
import fr.pacifista.api.support.tickets.client.enums.TicketStatus;
import fr.pacifista.api.support.tickets.client.enums.TicketType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_support_tickets")
public class PacifistaSupportTicket extends ApiEntity {

    @Column(nullable = false)
    private String object;

    @Column(nullable = false, name = "created_by_name")
    private String createdByName;

    @Column(nullable = false, name = "created_by_id")
    private String createdById;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, name = "creation_source")
    private TicketCreationSource creationSource;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TicketType type;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

}
