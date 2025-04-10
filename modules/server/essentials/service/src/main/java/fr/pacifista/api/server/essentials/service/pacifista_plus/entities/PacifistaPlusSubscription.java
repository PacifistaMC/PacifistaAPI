package fr.pacifista.api.server.essentials.service.pacifista_plus.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "pacifista_plus_subscription")
public class PacifistaPlusSubscription extends ApiEntity {

    @Column(name = "player_id", nullable = false, unique = true)
    private String playerId;

    @Column(nullable = false)
    private Integer months;

    @Column(name = "expiration_date")
    private Date expirationDate;

}
