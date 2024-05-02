package fr.pacifista.api.server.essentials.service.discord.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.tools.string.PasswordGenerator;
import fr.pacifista.api.server.essentials.client.discord.dtos.DiscordLinkDTO;
import fr.pacifista.api.server.essentials.service.discord.entities.DiscordLink;
import fr.pacifista.api.server.essentials.service.discord.mappers.DiscordLinkMapper;
import fr.pacifista.api.server.essentials.service.discord.repositories.DiscordLinkRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiscordLinkService extends ApiService<DiscordLinkDTO, DiscordLink, DiscordLinkMapper, DiscordLinkRepository> {

    private final PasswordGenerator passwordGenerator;

    public DiscordLinkService(DiscordLinkRepository repository,
                              DiscordLinkMapper mapper) {
        super(repository, mapper);
        this.passwordGenerator = new PasswordGenerator();
        this.passwordGenerator.setAlphaUpper(4);
        this.passwordGenerator.setAlphaDown(4);
        this.passwordGenerator.setNumbersAmount(3);
        this.passwordGenerator.setSpecialCharsAmount(2);
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<DiscordLink> entity) {
        for (final DiscordLink discordLink : entity) {
            if (discordLink.getId() == null) {
                this.checkIfAlreadyExists(discordLink);
                discordLink.setIsLinked(false);
                discordLink.setLinkingKey(this.passwordGenerator.generateRandomPassword());
            }
        }
    }

    private void checkIfAlreadyExists(@NonNull final DiscordLink newCreation) {
        final Optional<DiscordLink> existing = this.getRepository().findDiscordLinkByDiscordUserId(newCreation.getDiscordUserId());

        if (existing.isPresent()) {
            final DiscordLink existingLink = existing.get();

            if (!existingLink.getMinecraftUuid().equals(newCreation.getMinecraftUuid()) &&
                    this.getRepository().findDiscordLinkByMinecraftUuid(newCreation.getMinecraftUuid().toString()).isPresent()) {
                throw new ApiBadRequestException("Un lien existe déjà pour ce compte Minecraft.");
            }
            if (Boolean.TRUE.equals(existingLink.getIsLinked())) {
                throw new ApiBadRequestException("Un lien existe déjà pour cet utilisateur discord. Veuillez le délier avant d'en créer un nouveau.");
            } else {
                throw new ApiBadRequestException("Un lien est en attente de validation pour cet utilisateur discord. Veuillez le valider avant d'en créer un nouveau.");
            }
        } else {
            if (this.getRepository().findDiscordLinkByMinecraftUuid(newCreation.getMinecraftUuid().toString()).isPresent()) {
                throw new ApiBadRequestException("Un lien existe déjà pour ce compte Minecraft.");
            }
        }
    }
}
