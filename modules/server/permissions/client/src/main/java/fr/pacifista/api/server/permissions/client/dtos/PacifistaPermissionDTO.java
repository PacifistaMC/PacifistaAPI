package fr.pacifista.api.server.permissions.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PacifistaPermissionDTO extends ApiDTO {
    @NotNull
    private UUID roleId;

    @NotBlank
    private String permission;
}
