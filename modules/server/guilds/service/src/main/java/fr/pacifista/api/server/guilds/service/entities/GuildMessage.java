package fr.pacifista.api.server.guilds.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "pacifista_guilds_messages")
public class GuildMessage extends ApiEntity {

    @ManyToOne
    @JoinColumn(nullable = false, name = "guild_id")
    private Guild guild;

    @Column(name = "player_uuid", nullable = false)
    private String playerUuid;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, length = 5000)
    private String message;

    public void setPlayerUuid(UUID playerUuid) {
        if (playerUuid == null) {
            this.playerUuid = null;
        } else {
            this.playerUuid = playerUuid.toString();
        }
    }

    public UUID getPlayerUuid() {
        if (this.playerUuid == null) {
            return null;
        } else {
            return UUID.fromString(this.playerUuid);
        }
    }

}
