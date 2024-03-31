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

    public String getClaimDataId() {
        if (this.claimDataId == null) {
            return null;
        } else {
            return this.claimDataId.toString();
        }
    }

    public void setClaimDataId(String claimDataId) {
        if (claimDataId == null) {
            this.claimDataId = null;
        } else {
            this.claimDataId = UUID.fromString(claimDataId);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final ClaimDataConfigDTO other) {
            final boolean similar = this.claimDataId.equals(other.claimDataId) &&
                    this.explosionEnabled.equals(other.explosionEnabled) &&
                    this.fireSpreadEnabled.equals(other.fireSpreadEnabled) &&
                    this.mobGriefingEnabled.equals(other.mobGriefingEnabled) &&
                    this.pvpEnabled.equals(other.pvpEnabled) &&
                    this.publicAccess.equals(other.publicAccess) &&
                    this.publicInteractButtons.equals(other.publicInteractButtons) &&
                    this.publicInteractDoorsTrapDoors.equals(other.publicInteractDoorsTrapDoors) &&
                    this.publicInteractChests.equals(other.publicInteractChests) &&
                    this.animalProtection.equals(other.animalProtection) &&
                    this.griefProtection.equals(other.griefProtection);
            final UUID id = getId();

            if (id == null) {
                return similar;
            } else {
                return similar && id.equals(other.getId());
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int hash = getId() == null ? 0 : getId().hashCode();
        return 13 +
                this.claimDataId.hashCode() +
                this.explosionEnabled.hashCode() +
                this.fireSpreadEnabled.hashCode() +
                this.mobGriefingEnabled.hashCode() +
                this.pvpEnabled.hashCode() +
                this.publicAccess.hashCode() +
                this.publicInteractButtons.hashCode() +
                this.publicInteractDoorsTrapDoors.hashCode() +
                this.publicInteractChests.hashCode() +
                this.animalProtection.hashCode() +
                this.griefProtection.hashCode() +
                hash;
    }

    @Override
    public String toString() {
        return "ClaimDataConfigDTO{" +
                "claimDataId=" + this.claimDataId +
                ", explosionEnabled=" + this.explosionEnabled +
                ", fireSpreadEnabled=" + this.fireSpreadEnabled +
                ", mobGriefingEnabled=" + this.mobGriefingEnabled +
                ", pvpEnabled=" + this.pvpEnabled +
                ", publicAccess=" + this.publicAccess +
                ", publicInteractButtons=" + this.publicInteractButtons +
                ", publicInteractDoorsTrapDoors=" + this.publicInteractDoorsTrapDoors +
                ", publicInteractChests=" + this.publicInteractChests +
                ", animalProtection=" + this.animalProtection +
                ", griefProtection=" + this.griefProtection +
                ", id=" + getId() +
                '}';
    }
}
