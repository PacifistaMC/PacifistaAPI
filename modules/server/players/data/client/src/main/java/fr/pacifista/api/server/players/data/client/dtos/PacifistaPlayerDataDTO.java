package fr.pacifista.api.server.players.data.client.dtos;

import fr.pacifista.api.core.client.dtos.MinecraftPlayerDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

@Getter
@Setter
public class PacifistaPlayerDataDTO extends MinecraftPlayerDTO {

    /**
     * The player's play time in seconds.
     */
    @NotNull
    private Long playTime;

    /**
     * The player's last connection.
     */
    @NotNull
    private Date lastConnection;

    /**
     * The player's first connection.
     */
    @NotNull
    private Date firstConnection;

    /**
     * Boolean if the player accepts the /pay commands
     */
    @NotNull
    private Boolean acceptPayments;

    /**
     * Boolean if the player accepts the /tpa commands
     */
    @NotNull
    private Boolean acceptTeleportation;

    /**
     * Boolean if the player accept having pings when someone write his username in chat
     */
    @NotNull
    private Boolean acceptPingSoundTagMessage;

    /**
     * The player's skull item serialized.
     */
    @Nullable
    private String playerSkullItemSerialized;

}
