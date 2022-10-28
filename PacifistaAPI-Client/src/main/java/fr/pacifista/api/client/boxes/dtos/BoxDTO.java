package fr.pacifista.api.client.boxes.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import fr.pacifista.api.client.core.enums.ServerGameMode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class BoxDTO extends ApiDTO {
    @NotBlank
    private String boxName;

    @NotBlank
    private String boxDisplayName;

    @NotBlank
    private String boxDescription;

    @NotNull
    private Integer dropAmount;

    @NotNull
    private ServerGameMode gameMode;

    private List<BoxRewardDTO> rewards;

}
