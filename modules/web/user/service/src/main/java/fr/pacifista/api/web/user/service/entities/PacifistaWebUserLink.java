package fr.pacifista.api.web.user.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "pacifista_web_user_link")
public class PacifistaWebUserLink extends ApiEntity {

    @Column(name = "funix_prod_user_id", nullable = false, unique = true)
    private String funixProdUserId;

    @Column(name = "minecraft_uuid", nullable = false, unique = true)
    private String minecraftUuid;

    @Column(name = "linked", nullable = false)
    private Boolean linked;

    @Column(name = "link_key", nullable = false, unique = true)
    private String linkKey;

    public UUID getFunixProdUserId() {
        if (funixProdUserId == null) {
            return null;
        } else {
            return UUID.fromString(funixProdUserId);
        }
    }

    public UUID getMinecraftUuid() {
        if (minecraftUuid == null) {
            return null;
        } else {
            return UUID.fromString(minecraftUuid);
        }
    }

    public void setFunixProdUserId(UUID funixProdUserId) {
        if (funixProdUserId == null) {
            this.funixProdUserId = null;
        } else {
            this.funixProdUserId = funixProdUserId.toString();
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
