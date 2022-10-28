package fr.pacifista.api.client.guilds.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class GuildDTO extends ApiDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private List<GuildHomeDTO> homes;

    private List<GuildMemberDTO> members;

    private GuildExperienceDTO experience;

    @NotNull
    private Double money;

}
