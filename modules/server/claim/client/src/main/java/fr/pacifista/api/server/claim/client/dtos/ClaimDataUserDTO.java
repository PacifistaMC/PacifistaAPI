package fr.pacifista.api.server.claim.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.server.claim.client.enums.ClaimDataUserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ClaimDataUserDTO extends ApiDTO {

    /**
     * Mojang uuid player
     */
    private UUID playerId;

    /**
     * Role of the user in the claim
     */
    private ClaimDataUserRole role;

}
