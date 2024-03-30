package fr.pacifista.api.server.claim.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ClaimDataConfigDTO extends ApiDTO {

    @NotNull
    private UUID claimDataId;

    @NotNull
    private Boolean explosionEnabled;

    @NotNull
    private Boolean fireSpreadEnabled;

    @NotNull
    private Boolean mobGriefingEnabled;

    @NotNull
    private Boolean pvpEnabled;

    @NotNull
    private Boolean publicAccess;

    @NotNull
    private Boolean publicInteractButtons;

    @NotNull
    private Boolean publicInteractDoorsTrapDoors;

    @NotNull
    private Boolean publicInteractChests;

    @NotNull
    private Boolean animalProtection;

    @NotNull
    private Boolean griefProtection;

    public ClaimDataConfigDTO(final ClaimDataDTO dto,
                              final Boolean explosionEnabled,
                              final Boolean fireSpreadEnabled,
                              final Boolean mobGriefingEnabled,
                              final Boolean pvpEnabled,
                              final Boolean publicAccess,
                              final Boolean publicInteractButtons,
                              final Boolean publicInteractDoorsTrapDoors,
                              final Boolean publicInteractChests,
                              final Boolean animalProtection,
                              final Boolean griefProtection) {
        this.claimDataId = dto.getId();
        this.explosionEnabled = explosionEnabled;
        this.fireSpreadEnabled = fireSpreadEnabled;
        this.mobGriefingEnabled = mobGriefingEnabled;
        this.pvpEnabled = pvpEnabled;
        this.publicAccess = publicAccess;
        this.publicInteractButtons = publicInteractButtons;
        this.publicInteractDoorsTrapDoors = publicInteractDoorsTrapDoors;
        this.publicInteractChests = publicInteractChests;
        this.animalProtection = animalProtection;
        this.griefProtection = griefProtection;
    }

    public ClaimDataConfigDTO(final ClaimDataDTO dto) {
        this.claimDataId = dto.getId();
        this.explosionEnabled = false;
        this.fireSpreadEnabled = false;
        this.mobGriefingEnabled = false;
        this.pvpEnabled = false;
        this.publicAccess = true;
        this.publicInteractButtons = true;
        this.publicInteractDoorsTrapDoors = true;
        this.publicInteractChests = false;
        this.animalProtection = true;
        this.griefProtection = true;
    }

}
