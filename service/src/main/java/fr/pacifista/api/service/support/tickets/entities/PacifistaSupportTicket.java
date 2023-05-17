package fr.pacifista.api.service.support.tickets.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.client.support.tickets.enums.TicketCreationSource;
import fr.pacifista.api.client.support.tickets.enums.TicketStatus;
import fr.pacifista.api.client.support.tickets.enums.TicketType;
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
