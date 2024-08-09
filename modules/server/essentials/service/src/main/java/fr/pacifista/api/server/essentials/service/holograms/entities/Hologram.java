package fr.pacifista.api.server.essentials.service.holograms.entities;

import fr.pacifista.api.core.service.entities.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "pacifista_holograms")
public class Hologram extends Location {

    @Column(name = "text_serialized", nullable = false, length = 10000)
    private String textSerialized;

    @ManyToOne
    @JoinColumn(name = "parent_hologram_id")
    private Hologram parentHologram;

    @OneToMany(mappedBy = "parentHologram", orphanRemoval = true)
    private List<Hologram> childHolograms;

}
