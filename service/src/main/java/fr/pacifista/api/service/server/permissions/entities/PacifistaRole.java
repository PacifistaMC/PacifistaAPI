package fr.pacifista.api.service.server.permissions.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "pacifista_role")
public class PacifistaRole extends ApiEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "player_name_color", nullable = false)
    private String playerNameColor;

    @Column(nullable = false)
    private String prefix;

    @Column(nullable = false)
    private String power;

    @Column(nullable = false, name = "staff_rank")
    private Boolean staffRank;

    @OneToMany(mappedBy = "role", orphanRemoval = true)
    private List<PacifistaPermission> permissions;

}
