package fr.pacifista.api.server.essentials.service.command_sender.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.core.client.enums.ServerType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "CommandToSend")
public class CommandToSend extends ApiEntity {

    @Column(name = "command", nullable = false)
    private String command;

    @Column(name = "server_type", nullable = false)
    private ServerType serverType;

    @Column(name = "is_command_for_proxy", nullable = false)
    private Boolean isCommandForProxy;

    @Column(name = "executed", nullable = false)
    private Boolean executed;

    @Column(name = "creation_canal", nullable = false)
    private String creationCanal;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final CommandToSend commandToSend) {
            return command.equals(commandToSend.command) &&
                    serverType.equals(commandToSend.serverType) &&
                    isCommandForProxy.equals(commandToSend.isCommandForProxy) &&
                    executed.equals(commandToSend.executed) &&
                    creationCanal.equals(commandToSend.creationCanal) &&
                    super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() +
                (command == null ? 0 : command.hashCode()) +
                (serverType == null ? 0 : serverType.hashCode()) +
                (isCommandForProxy == null ? 0 : isCommandForProxy.hashCode()) +
                (executed == null ? 0 : executed.hashCode()) +
                (creationCanal == null ? 0 : creationCanal.hashCode()) + 13;
    }
}
