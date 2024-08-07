package fr.pacifista.api.server.claim.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.core.client.enums.ServerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "claim_data")
public class ClaimData extends ApiEntity {

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ClaimData parent;

    @Column(name = "server_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServerType serverType;

    @Column(name = "world_id", nullable = false)
    private String worldId;

    @Column(name = "lesser_boundary_corner_x", nullable = false)
    private Double lesserBoundaryCornerX;

    @Column(name = "lesser_boundary_corner_z", nullable = false)
    private Double lesserBoundaryCornerZ;

    @Column(name = "greater_boundary_corner_x", nullable = false)
    private Double greaterBoundaryCornerX;

    @Column(name = "greater_boundary_corner_z", nullable = false)
    private Double greaterBoundaryCornerZ;

    @OneToOne(mappedBy = "claim", orphanRemoval = true)
    private ClaimDataConfig config;

    @OneToMany(mappedBy = "claim", orphanRemoval = true)
    private List<ClaimDataUser> users;

}
