package fr.pacifista.api.server.claim.client.dtos;

import fr.pacifista.api.core.client.dtos.LocationDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimPhantomPreventionBlockDTO extends LocationDTO {

    /**
     * The fuel of the phantom prevention block to run.
     */
    @NotNull
    private Integer fuel;

}
