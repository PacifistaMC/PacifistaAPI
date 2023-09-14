package fr.pacifista.api.server.players.sync.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_player_enderchest_data")
public class PlayerEnderchestData extends PlayerDataServer {
    @Column(name = "enderchest_serialized", nullable = false, length = 100000)
    private String enderchestSerialized;

    @Column(name = "enderchest_paladin_serialized", nullable = false, length = 100000)
    private String enderchestPaladinSerialized;

    @Column(name = "enderchest_elite_serialized", nullable = false, length = 100000)
    private String enderchestEliteSerialized;

    @Column(name = "enderchest_legendaire_serialized", nullable = false, length = 100000)
    private String enderchestLegendaireSerialized;

    @Column(name = "enderchest_mine_serialized", nullable = false, length = 100000)
    private String enderchestMineSerialized;


}
