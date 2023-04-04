package fr.pacifista.api.service.permissions.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

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
