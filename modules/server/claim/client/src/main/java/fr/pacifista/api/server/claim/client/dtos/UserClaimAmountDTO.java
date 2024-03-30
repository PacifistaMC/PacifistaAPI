package fr.pacifista.api.server.claim.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserClaimAmountDTO extends ApiDTO {

    @NotNull
    private UUID playerId;

    @NotNull
    @Positive
    private Integer blocksAmount;

    public UserClaimAmountDTO(final @NonNull UUID playerId,
                              final @NonNull Integer blocksAmount) {
        this.playerId = playerId;
        this.blocksAmount = blocksAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final UserClaimAmountDTO other) {
            final boolean similar = playerId.equals(other.playerId) &&
                    blocksAmount.equals(other.blocksAmount);
            final UUID id = getId();

            if (id == null) {
                return similar;
            } else {
                return similar && id.equals(other.getId());
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int idHash = getId() == null ? 0 : getId().hashCode();
        return 13 + playerId.hashCode() + blocksAmount.hashCode() + idHash;
    }

    @Override
    public String toString() {
        return "UserClaimAmountDTO{" +
                "playerId=" + playerId +
                ", blocksAmount=" + blocksAmount +
                ", id=" + getId() +
                '}';
    }
}
