package fr.pacifista.api.server.essentials.client.pacifista_plus.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacifistaPlusSubscriptionDTO extends ApiDTO {

    /**
     * The player id. (mojang)
     */
    @NotNull(message = "playerId is required")
    private UUID playerId;

    /**
     * The number of months for the subscription.
     */
    @NotNull(message = "months is required")
    private Integer months;

    /**
     * The date when the subscription expires. If null, the subscription is considered as lifetime.
     */
    @Nullable
    private Date expirationDate;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final PacifistaPlusSubscriptionDTO other) {
            return super.equals(obj) && playerId.equals(other.playerId) && (Objects.equals(expirationDate, other.expirationDate));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + playerId.hashCode() + (expirationDate == null ? 0 : expirationDate.hashCode());
    }

    @Override
    public String toString() {
        return "PacifistaPlusSubscriptionDTO{" +
                "playerId=" + playerId +
                ", expirationDate=" + expirationDate +
                ", id=" + super.getId() +
                '}';
    }
}
