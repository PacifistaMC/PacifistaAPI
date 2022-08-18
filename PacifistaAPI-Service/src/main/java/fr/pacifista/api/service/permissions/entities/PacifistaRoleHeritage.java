package fr.pacifista.api.service.permissions.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "pacifista_role_heritage")
public class PacifistaRoleHeritage extends ApiEntity {

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private PacifistaRole role;

    @ManyToOne
    @JoinColumn(name = "role_heritage_id")
    private PacifistaRole heritage;

}
