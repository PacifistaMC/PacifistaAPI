package fr.pacifista.api.server.essentials.client.discord.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DiscordLinkDTO extends ApiDTO {

    /**
     * Discord user id
     */
    @NotBlank(message = "discordUserId is mandatory")
    private String discordUserId;

    /**
     * Minecraft uuid
     */
    @NotNull(message = "minecraftUuid is mandatory")
    private UUID minecraftUuid;

    public DiscordLinkDTO(@NonNull final String discordUserId,
                          @NonNull final UUID minecraftUuid) {
        this.discordUserId = discordUserId;
        this.minecraftUuid = minecraftUuid;
    }

    public String getMinecraftUuid() {
        if (minecraftUuid == null) {
            return null;
        } else {
            return minecraftUuid.toString();
        }
    }

    public void setMinecraftUuid(String minecraftUuid) {
        if (minecraftUuid == null) {
            this.minecraftUuid = null;
        } else {
            this.minecraftUuid = UUID.fromString(minecraftUuid);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final DiscordLinkDTO discordLinkDTO) {
            return discordUserId.equals(discordLinkDTO.discordUserId) &&
                    minecraftUuid.equals(discordLinkDTO.minecraftUuid) &&
                    super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() +
                (discordUserId == null ? 0 : discordUserId.hashCode()) +
                (minecraftUuid == null ? 0 : minecraftUuid.hashCode()) + 13;
    }
}
