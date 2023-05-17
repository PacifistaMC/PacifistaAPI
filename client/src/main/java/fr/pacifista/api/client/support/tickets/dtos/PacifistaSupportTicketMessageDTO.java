package fr.pacifista.api.client.support.tickets.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacifistaSupportTicketMessageDTO extends ApiDTO {

    private PacifistaSupportTicketDTO ticket;

    @NotBlank
    private String writtenByName;

    @NotBlank
    private String writtenById;

    @NotBlank
    private String message;

}
