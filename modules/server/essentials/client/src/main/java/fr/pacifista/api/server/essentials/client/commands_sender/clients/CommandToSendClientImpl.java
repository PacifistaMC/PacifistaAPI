package fr.pacifista.api.server.essentials.client.commands_sender.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.essentials.client.commands_sender.dtos.CommandToSendDTO;

public class CommandToSendClientImpl extends FeignImpl<CommandToSendDTO, CommandToSendClient> {

    public static final String PATH = "essentials/command-sender";

    public CommandToSendClientImpl() {
        super(PATH, CommandToSendClient.class);
    }

}
