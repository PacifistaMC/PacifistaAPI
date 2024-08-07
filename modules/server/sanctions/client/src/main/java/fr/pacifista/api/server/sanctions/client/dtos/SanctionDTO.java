package fr.pacifista.api.server.sanctions.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.server.sanctions.client.enums.SanctionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class SanctionDTO extends ApiDTO {

    /**
     * Player sanctionned UUID
     */
    @NotNull
    private UUID playerSanctionUuid;

    /**
     * Sanction reason
     * Used to know why the player received this one
     */
    @NotBlank
    private String reason;

    /**
     * Sanction type
     * Used to determine what type of sanction it is
     */
    @NotNull
    private SanctionType sanctionType;

    /**
     * Last Player UUID who updated the sanction
     */
    private UUID playerActionUuid;

    /**
     * Know if the sanction is active
     */
    @NotNull
    private Boolean active;

    /**
     * Sanction expiration date
     * If it's null it's tells that the sanction is permanent
     */
    private Date expirationDate;
}
