package fr.pacifista.api.support.tickets.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.support.tickets.client.enums.TicketCreationSource;
import fr.pacifista.api.support.tickets.client.enums.TicketStatus;
import fr.pacifista.api.support.tickets.client.enums.TicketType;
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
