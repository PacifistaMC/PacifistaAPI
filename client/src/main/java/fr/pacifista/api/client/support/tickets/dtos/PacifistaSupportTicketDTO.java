package fr.pacifista.api.client.support.tickets.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import fr.pacifista.api.client.support.tickets.enums.TicketCreationSource;
import fr.pacifista.api.client.support.tickets.enums.TicketStatus;
import fr.pacifista.api.client.support.tickets.enums.TicketType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacifistaSupportTicketDTO extends ApiDTO {

    @NotBlank
    private String object;

    @NotBlank
    private String createdByName;

    private String createdById;

    @NotNull
    private TicketCreationSource creationSource;

    @NotNull
    private TicketType type;

    @NotNull
    private TicketStatus status;

}
