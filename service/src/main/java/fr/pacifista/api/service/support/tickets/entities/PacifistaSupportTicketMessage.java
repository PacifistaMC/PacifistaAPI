package fr.pacifista.api.service.support.tickets.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_support_tickets_messages")
public class PacifistaSupportTicketMessage extends ApiEntity {

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private PacifistaSupportTicket ticket;

    @Column(nullable = false, name = "written_by_name")
    private String writtenByName;

    @Column(nullable = false, name = "written_by_id")
    private String writtenById;

    @Column(nullable = false, length = 10000)
    private String message;

}
