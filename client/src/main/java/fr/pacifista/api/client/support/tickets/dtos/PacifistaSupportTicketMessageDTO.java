package fr.pacifista.api.client.support.tickets.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacifistaSupportTicketMessageDTO extends ApiDTO {

    private PacifistaSupportTicketDTO ticket;

    @NotBlank
    private String writtenBy;

    @NotBlank
    private String message;

}
