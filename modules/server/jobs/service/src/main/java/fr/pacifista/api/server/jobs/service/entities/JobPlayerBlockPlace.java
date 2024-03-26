package fr.pacifista.api.server.jobs.service.entities;

import fr.pacifista.api.core.service.entities.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "job_players_blocks_place")
public class JobPlayerBlockPlace extends Location {

    @Column(name = "block_type", nullable = false)
    private String blockType;

}
