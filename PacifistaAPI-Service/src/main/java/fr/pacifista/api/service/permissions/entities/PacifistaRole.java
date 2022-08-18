package fr.pacifista.api.service.permissions.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "role", orphanRemoval = true)
    private List<PacifistaRoleHeritage> heritages;

}
