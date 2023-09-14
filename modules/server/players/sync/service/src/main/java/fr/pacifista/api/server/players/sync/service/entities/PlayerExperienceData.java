package fr.pacifista.api.server.players.sync.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_player_experience_data")
public class PlayerExperienceData extends PlayerDataServer {

    @Column(name = "total_experience", nullable = false)
    private Integer totalExperience;

    @Column(nullable = false)
    private Float exp;

    @Column(nullable = false)
    private Integer level;

}
