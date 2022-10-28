package fr.pacifista.api.service.guilds.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import fr.pacifista.api.client.guilds.enums.GuildRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "pacifista_guilds_members")
public class GuildMember extends ApiEntity {

    @ManyToOne
    @JoinColumn(nullable = false, name = "guild_id")
    private Guild guild;

    @Column(nullable = false, name = "player_uuid")
    private String playerUuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GuildRole role;

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
