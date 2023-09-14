package fr.pacifista.api.server.players.sync.service.entities;

import fr.pacifista.api.core.client.enums.enums.ServerGameMode;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class PlayerDataServer extends PlayerData {
    @Enumerated(EnumType.STRING)
    @Column(name = "game_mode", nullable = false)
    private ServerGameMode gameMode;
}
