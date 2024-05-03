package fr.pacifista.api.server.essentials.service.discord.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "discord_link")
public class DiscordLink extends ApiEntity {

    /**
     * Discord user id
     */
    @Column(name = "discord_user_id", nullable = false, unique = true)
    private String discordUserId;

    /**
     * Minecraft uuid
     */
    @Column(name = "minecraft_uuid", nullable = false, unique = true)
    private String minecraftUuid;

    /**
     * Is linked (pending or not)
     */
    @Column(name = "is_linked", nullable = false)
    private Boolean isLinked;

    @Column(name = "linking_key", unique = true)
    private String linkingKey;

    public UUID getMinecraftUuid() {
        if (minecraftUuid == null) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final DiscordLink discordLink) {
            return (discordUserId != null && discordLink.discordUserId != null && discordUserId.equals(discordLink.discordUserId)) &&
                    (minecraftUuid != null && discordLink.minecraftUuid != null && minecraftUuid.equals(discordLink.minecraftUuid)) &&
                    (isLinked != null && discordLink.isLinked != null && isLinked.equals(discordLink.isLinked)) &&
                    (linkingKey != null && discordLink.linkingKey != null && linkingKey.equals(discordLink.linkingKey)) &&
                    super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() +
                (discordUserId == null ? 0 : discordUserId.hashCode()) +
                (minecraftUuid == null ? 0 : minecraftUuid.hashCode()) +
                (isLinked == null ? 0 : isLinked.hashCode()) +
                13;
    }

}
