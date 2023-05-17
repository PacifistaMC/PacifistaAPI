package fr.pacifista.api.client.server.boxes.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.client.core.enums.ServerGameMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
