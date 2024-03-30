package fr.pacifista.api.server.claim.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.server.claim.client.enums.ClaimDataUserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ClaimDataUserDTO extends ApiDTO {

    /**
     * Mojang uuid player
     */
    @NotNull
    private UUID playerId;

    /**
     * Claim data id
     */
    @NotNull
    private UUID claimDataId;

    /**
     * Role of the user in the claim
     */
    @NotNull
    private ClaimDataUserRole role;

    public ClaimDataUserDTO(final @NonNull UUID playerId,
                            final @NonNull UUID claimDataId) {
        this.playerId = playerId;
        this.claimDataId = claimDataId;
        this.role = ClaimDataUserRole.MEMBER;
    }

    public ClaimDataUserDTO(final @NonNull UUID playerId,
                            final @NonNull ClaimDataDTO claimDataDTO) {
        this.playerId = playerId;
        this.claimDataId = claimDataDTO.getId();
        this.role = ClaimDataUserRole.MEMBER;
    }

    public ClaimDataUserDTO(final @NonNull UUID playerId,
                            final @NonNull ClaimDataDTO claimDataDTO,
                            final @NonNull ClaimDataUserRole role) {
        this.playerId = playerId;
        this.claimDataId = claimDataDTO.getId();
        this.role = role;
    }

    public ClaimDataUserDTO(final @NonNull UUID playerId,
                            final @NonNull UUID claimDataId,
                            final @NonNull ClaimDataUserRole role) {
        this.playerId = playerId;
        this.claimDataId = claimDataId;
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final ClaimDataUserDTO other) {
            return playerId.equals(other.playerId) &&
                    claimDataId.equals(other.claimDataId) &&
                    role.equals(other.role) &&
                    this.getId().equals(other.getId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 13 + this.playerId.hashCode() +
                this.claimDataId.hashCode() +
                this.role.hashCode() +
                this.getId().hashCode();
    }

    @Override
    public String toString() {
        return "ClaimDataUserDTO{" +
                "playerId=" + playerId +
                ", claimDataId=" + claimDataId +
                ", role=" + role +
                ", id=" + getId() +
                '}';
    }
}
