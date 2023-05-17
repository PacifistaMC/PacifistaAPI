package fr.pacifista.api.client.server.permissions.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PacifistaPlayerRoleDTO extends ApiDTO {

    @NotNull
    private UUID playerUuid;

    @NotNull
    private PacifistaRoleDTO role;
}
