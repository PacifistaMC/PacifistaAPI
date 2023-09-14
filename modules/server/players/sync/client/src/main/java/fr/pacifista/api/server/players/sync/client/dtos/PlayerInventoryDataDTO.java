package fr.pacifista.api.server.players.sync.client.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerInventoryDataDTO extends PlayerDataServerDTO {
    @NotBlank
    private String inventory;

    @NotBlank
    private String armor;
}
