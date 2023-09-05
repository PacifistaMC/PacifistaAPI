package fr.pacifista.api.serverplayers.data.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class MinecraftPlayer extends ApiEntity {

    @Column(name = "minecraft_uuid", length = 50, nullable = false)
    private String minecraftUuid;

    @Column(name = "minecraft_username", length = 200, nullable = false)
    private String minecraftUsername;

    public UUID getMinecraftUuid() {
        if (this.minecraftUuid == null) {
            return null;
        } else {
            return UUID.fromString(minecraftUuid);
        }
    }

    public void setMinecraftUuid(UUID minecraftUuid) {
        if (minecraftUuid == null) {
            this.minecraftUuid = null;
        } else {
            this.minecraftUuid = minecraftUuid.toString();
        }
    }
}
