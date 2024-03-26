package fr.pacifista.api.server.jobs.client.dtos;

import fr.pacifista.api.core.client.dtos.LocationDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPlayerBlockPlaceDTO extends LocationDTO {

    @NotNull(message = "blockType est requis")
    private String blockType;

}
