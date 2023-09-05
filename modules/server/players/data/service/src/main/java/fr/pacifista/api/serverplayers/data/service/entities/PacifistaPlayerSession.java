package fr.pacifista.api.serverplayers.data.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_player_session")
public class PacifistaPlayerSession extends MinecraftPlayer {

    /**
     * Date of connection.
     */
    @Column(nullable = false, name = "connected_at", updatable = false)
    private Long connectedAt;

    /**
     * Date of disconnection.
     */
    @Column(nullable = false, name = "disconnected_at", updatable = false)
    private Long disconnectedAt;
}
