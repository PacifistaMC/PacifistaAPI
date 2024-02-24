package fr.pacifista.api.server.players.data.service.entities;

import fr.pacifista.api.core.service.entities.MinecraftPlayer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "pacifista_player_session")
public class PacifistaPlayerSession extends MinecraftPlayer {

    /**
     * Date of connection.
     */
    @Column(nullable = false, name = "connected_at", updatable = false)
    private Date connectedAt;

    /**
     * Date of disconnection.
     */
    @Column(nullable = false, name = "disconnected_at", updatable = false)
    private Date disconnectedAt;
}
