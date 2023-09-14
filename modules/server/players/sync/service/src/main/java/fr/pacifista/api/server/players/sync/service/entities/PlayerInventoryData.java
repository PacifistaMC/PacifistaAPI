package fr.pacifista.api.server.players.sync.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_player_inventory_data")
public class PlayerInventoryData extends PlayerDataServer {

    @Column(nullable = false, length = 100000)
    private String inventory;

    @Column(nullable = false, length = 100000)
    private String armor;

}
