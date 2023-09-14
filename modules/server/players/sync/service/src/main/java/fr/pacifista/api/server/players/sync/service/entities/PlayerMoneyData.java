package fr.pacifista.api.server.players.sync.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_player_money_data")
public class PlayerMoneyData extends PlayerData {

    @Column(nullable = false)
    private Double money;

    @Column(nullable = false, name = "offline_money")
    private Double offlineMoney;

}
