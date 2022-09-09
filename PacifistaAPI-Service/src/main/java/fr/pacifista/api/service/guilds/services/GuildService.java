package fr.pacifista.api.service.guilds.services;

import fr.funixgaming.api.core.crud.services.ApiService;
import fr.funixgaming.api.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.client.guilds.dtos.GuildDTO;
import fr.pacifista.api.service.guilds.entities.Guild;
import fr.pacifista.api.service.guilds.mappers.GuildMapper;
import fr.pacifista.api.service.guilds.repositories.GuildRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GuildService extends ApiService<GuildDTO, Guild, GuildMapper, GuildRepository> {

    public GuildService(GuildRepository repository,
                        GuildMapper mapper) {
        super(repository, mapper);
    }

    protected Guild findGuildById(@Nullable UUID id) throws ApiBadRequestException {
        if (id == null) {
            throw new ApiBadRequestException("Vous n'avez pas spécifié d'id de guilde.");
        } else {
            return getRepository().findByUuid(
                    id.toString()
            ).orElseThrow(() -> new ApiBadRequestException("La guilde id " + id + " n'existe pas."));
        }
    }
}
