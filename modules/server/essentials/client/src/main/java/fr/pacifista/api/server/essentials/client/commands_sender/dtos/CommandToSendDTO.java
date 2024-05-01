package fr.pacifista.api.server.essentials.client.commands_sender.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.core.client.enums.ServerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommandToSendDTO extends ApiDTO {

    /**
     * La commande a envoyer sur le serveur Pacifista (Minecraft ou Proxy)
     */
    @NotBlank(message = "command is mandatory")
    private String command;

    /**
     * Le type de serveur sur lequel envoyer la commande
     */
    @NotNull(message = "serverType is mandatory")
    private ServerType serverType;

    /**
     * Si la commande est a envoyer sur le proxy
     */
    @NotNull(message = "isCommandForProxy is mandatory")
    private Boolean isCommandForProxy;

    /**
     * Si la commande a deja ete executee
     */
    @NotNull(message = "executed is mandatory")
    private Boolean executed;

    /**
     * Le canal de creation de la commande
     */
    @NotBlank(message = "creationCanal is mandatory")
    private String creationCanal;

    public CommandToSendDTO(@NotBlank final String command,
                            @NotNull final ServerType serverType,
                            @NotNull final Boolean isCommandForProxy,
                            @NotNull final Boolean executed,
                            @NotBlank final String creationCanal) {
        this.command = command;
        this.serverType = serverType;
        this.isCommandForProxy = isCommandForProxy;
        this.executed = executed;
        this.creationCanal = creationCanal;
    }

    public CommandToSendDTO(@NotBlank final String command,
                            @NotNull final ServerType serverType,
                            @NotNull final Boolean isCommandForProxy,
                            @NotBlank final String creationCanal) {
        this.command = command;
        this.serverType = serverType;
        this.isCommandForProxy = isCommandForProxy;
        this.executed = false;
        this.creationCanal = creationCanal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final CommandToSendDTO commandToSendDTO) {
            return command.equals(commandToSendDTO.command) &&
                    serverType.equals(commandToSendDTO.serverType) &&
                    isCommandForProxy.equals(commandToSendDTO.isCommandForProxy) &&
                    executed.equals(commandToSendDTO.executed) &&
                    creationCanal.equals(commandToSendDTO.creationCanal) &&
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
                (creationCanal == null ? 0 : creationCanal.hashCode()) +
                13;
    }
}
