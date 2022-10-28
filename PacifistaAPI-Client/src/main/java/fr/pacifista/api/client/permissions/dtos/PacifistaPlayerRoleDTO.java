package fr.pacifista.api.client.permissions.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class PacifistaPlayerRoleDTO extends ApiDTO {

    @NotNull
    private UUID playerUuid;

    @NotNull
    private PacifistaRoleDTO role;
}
