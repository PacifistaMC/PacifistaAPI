package fr.pacifista.api.web.vote.service.services;

import com.funixproductions.core.exceptions.ApiException;
import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.server.essentials.client.commands_sender.clients.CommandToSendInternalClient;
import fr.pacifista.api.server.essentials.client.commands_sender.dtos.CommandToSendDTO;
import fr.pacifista.api.web.vote.service.entities.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteValidatedSuccessService {

    private final CommandToSendInternalClient commandSenderClient;

    protected void sendRewards(final List<Vote> rewards) {
        final List<CommandToSendDTO> commandsToSend = new ArrayList<>();

        for (final Vote vote : rewards) {
            commandsToSend.add(createCommand(vote));
        }

        try {
            this.commandSenderClient.create(commandsToSend);
        } catch (Exception e) {
            final List<String> users = new ArrayList<>();
            for (final Vote vote : rewards) {
                users.add(vote.getUsername());
            }

            throw new ApiException("Impossible de créer les commandes liés aux votes. Utilisateurs concernés : " + String.join(", ", users) + ".", e);
        }
    }

    private CommandToSendDTO createCommand(final Vote vote) {
        return new CommandToSendDTO(
                String.format(
                        "vote reward %s",
                        vote.getUsername()
                ),
                ServerType.SURVIE_ALPHA,
                false,
                "pacifista-api-vote"
        );
    }

}
