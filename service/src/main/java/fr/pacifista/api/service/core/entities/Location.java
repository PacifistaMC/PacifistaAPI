package fr.pacifista.api.service.core.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.client.core.enums.ServerType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class Location extends ApiEntity {
    @Column(nullable = false, name = "world_uuid")
    private String worldUuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "server_type")
    private ServerType serverType;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @Column(nullable = false)
    private Double z;

    @Column(nullable = false)
    private Float yaw;

    @Column(nullable = false)
    private Float pitch;

    public void setWorldUuid(UUID worldUuid) {
        if (worldUuid != null) {
            this.worldUuid = worldUuid.toString();
        } else {
            this.worldUuid = null;
        }
    }

    public UUID getWorldUuid() {
        if (this.worldUuid != null) {
            return UUID.fromString(this.worldUuid);
        } else {
            return null;
        }
    }
}
