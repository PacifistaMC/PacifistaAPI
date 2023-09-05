package fr.pacifista.api.serverplayers.data.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "pacifista_player_data")
public class PacifistaPlayerData extends MinecraftPlayer {

    /**
     * The player's play time in seconds.
     */
    @Column(nullable = false, name = "play_time")
    private Long playTime;

    /**
     * The player's last connection.
     */
    @Column(nullable = false, name = "last_connection")
    private Date lastConnection;

    /**
     * The player's first connection.
     */
    @Column(nullable = false, name = "first_connection", updatable = false)
    private Date firstConnection;

    /**
     * Boolean if the player accepts the /pay commands
     */
    @Column(nullable = false, name = "accept_payments")
    private Boolean acceptPayments;

    /**
     * Boolean if the player accepts the /tpa commands
     */
    @Column(nullable = false, name = "accept_teleportation")
    private Boolean acceptTeleportation;

    /**
     * Boolean if the player accept having pings when someone write his username in chat
     */
    @Column(nullable = false, name = "accept_ping_sound_tag_message")
    private Boolean acceptPingSoundTagMessage;

}
