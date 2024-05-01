package fr.pacifista.api.server.essentials.service.command_sender.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.essentials.service.command_sender.entities.CommandToSend;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandToSendRepository extends ApiRepository<CommandToSend> {
}
