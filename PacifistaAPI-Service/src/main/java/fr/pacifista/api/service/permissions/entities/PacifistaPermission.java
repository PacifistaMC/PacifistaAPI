package fr.pacifista.api.service.permissions.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "pacifista_permission")
public class PacifistaPermission extends ApiEntity {

    @ManyToOne
    @JoinColumn(nullable = false, name = "role_id")
    private PacifistaRole role;

    @Column(nullable = false)
    private String permission;

}
