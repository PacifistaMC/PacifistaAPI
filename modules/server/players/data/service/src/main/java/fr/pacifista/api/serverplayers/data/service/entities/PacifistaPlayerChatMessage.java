package fr.pacifista.api.serverplayers.data.service.entities;

import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.core.service.entities.MinecraftPlayer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_player_chat_message")
public class PacifistaPlayerChatMessage extends MinecraftPlayer {

    @Column(length = 1000, nullable = false)
    private String message;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, name = "server_type")
    private ServerType serverType;

    @Column(nullable = false, name = "is_command")
    private Boolean isCommand;
}
