package fr.pacifista.api.service.server.sanctions.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import fr.pacifista.api.client.sanctions.dtos.SanctionDTO;
import fr.pacifista.api.client.sanctions.enums.SanctionType;
import fr.pacifista.api.service.core.converters.EncryptionString;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * For documentation please see
 * {@link SanctionDTO SanctionDTO}
 */
@Getter
@Setter
@Entity(name = "pacifista_sanctions")
public class Sanction extends ApiEntity {

    @Column(name = "player_sanction_uuid", nullable = false)
    private String playerSanctionUuid;

    @Convert(converter = EncryptionString.class)
    @Column(name = "player_sanction_ip", nullable = false)
    private String playerSanctionIp;

    @Column(nullable = false)
    private String reason;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "sanction_type", nullable = false)
    private SanctionType sanctionType;

    @Column(name = "player_action_uuid")
    private String playerActionUuid;

    @Convert(converter = EncryptionString.class)
    @Column(name = "player_action_ip")
    private String playerActionIp;

    @Column(name = "ip_sanction", nullable = false)
    private Boolean ipSanction;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "expiration_date")
    private Date expirationDate;

}
