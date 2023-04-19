package fr.pacifista.api.client.boxes.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BoxRewardDTO extends ApiDTO {
    @NotNull
    private UUID boxId;

    @NotBlank
    private String itemSerialized;

    @NotNull
    private Float rarity;
}
