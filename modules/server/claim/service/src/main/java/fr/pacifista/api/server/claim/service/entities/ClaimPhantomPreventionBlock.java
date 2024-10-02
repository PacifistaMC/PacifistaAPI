package fr.pacifista.api.server.claim.service.entities;

import fr.pacifista.api.core.service.entities.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "claim_phantom_prevention_blocks")
public class ClaimPhantomPreventionBlock extends Location {

    /**
     * The fuel of the phantom prevention block to run.
     */
    @Column(name = "fuel", nullable = false)
    private Integer fuel;

}
