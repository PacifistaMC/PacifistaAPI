package fr.pacifista.api.client.sanctions.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import fr.pacifista.api.client.sanctions.enums.SanctionType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
     * Player sanctionned IPv4 addess
     */
    @NotBlank
    private String playerSanctionIp;

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
     * Last Player IPv4 who updated the sanction
     */
    private String playerActionIp;

    /**
     * Know if the sanction is ip sanction
     * If it's true its tells that every player with this ip will be
     * affected by this sanction
     */
    @NotNull
    private Boolean ipSanction;

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
