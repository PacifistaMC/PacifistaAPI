package fr.pacifista.api.client.support.tickets.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import fr.pacifista.api.client.support.tickets.enums.TicketCreationSource;
import fr.pacifista.api.client.support.tickets.enums.TicketStatus;
import fr.pacifista.api.client.support.tickets.enums.TicketType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class PacifistaSupportTicketDTO extends ApiDTO {

    @NotBlank
    private String object;

    @NotBlank
    private String createdByName;

    private String createdById;

    @NonNull
    private TicketCreationSource creationSource;

    @NonNull
    private TicketType type;

    @NonNull
    private TicketStatus status;

}
