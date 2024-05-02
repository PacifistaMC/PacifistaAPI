package fr.pacifista.api.web.user.service.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import com.funixproductions.core.tools.string.PasswordGenerator;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import fr.pacifista.api.web.user.service.entities.PacifistaWebUserLink;
import fr.pacifista.api.web.user.service.mappers.PacifistaWebUserLinkMapper;
import fr.pacifista.api.web.user.service.repositories.PacifistaWebUserLinkRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PacifistaWebUserLinkService extends ApiService<PacifistaWebUserLinkDTO, PacifistaWebUserLink, PacifistaWebUserLinkMapper, PacifistaWebUserLinkRepository> {

    private final PasswordGenerator passwordGenerator;

    public PacifistaWebUserLinkService(PacifistaWebUserLinkRepository repository,
                                       PacifistaWebUserLinkMapper mapper) {
        super(repository, mapper);
        this.passwordGenerator = new PasswordGenerator();
        this.passwordGenerator.setAlphaDown(4);
        this.passwordGenerator.setAlphaUpper(4);
        this.passwordGenerator.setNumbersAmount(3);
        this.passwordGenerator.setSpecialCharsAmount(2);
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PacifistaWebUserLink> entity) {
        for (final PacifistaWebUserLink pacifistaWebUserLink : entity) {
            if (pacifistaWebUserLink.getId() == null) {
                this.checkIfAlreadyExists(pacifistaWebUserLink);
                pacifistaWebUserLink.setLinked(false);
                pacifistaWebUserLink.setLinkKey(this.passwordGenerator.generateRandomPassword());
            }
        }
    }

    @Transactional
    public void unlink(final UUID funixProdId) {
        final Optional<PacifistaWebUserLink> existing = this.getRepository().findByFunixProdUserId(funixProdId.toString());

        if (existing.isEmpty()) {
            throw new ApiNotFoundException("Aucun lien n'existe pour cet utilisateur. Veuillez en créer un avant de le délier");
        } else {
            this.getRepository().delete(existing.get());
        }
    }

    public PacifistaWebUserLinkDTO findLinkedFromFunixProdUser(final UUID funixProdId) {
        final Optional<PacifistaWebUserLink> existing = this.getRepository().findByFunixProdUserId(funixProdId.toString());

        if (existing.isEmpty()) {
            throw new ApiNotFoundException("Aucun lien n'existe pour cet utilisateur.");
        } else {
            return this.getMapper().toDto(existing.get());
        }
    }

    private void checkIfAlreadyExists(final PacifistaWebUserLink newCreation) throws ApiException {
        final Optional<PacifistaWebUserLink> existing = this.getRepository().findByFunixProdUserId(newCreation.getFunixProdUserId().toString());

        if (existing.isPresent()) {
            final PacifistaWebUserLink existingLink = existing.get();

            if (!existingLink.getMinecraftUuid().equals(newCreation.getMinecraftUuid()) && this.getRepository().findByMinecraftUuid(newCreation.getMinecraftUuid().toString()).isPresent()) {
                throw new ApiBadRequestException("Un lien existe déjà pour ce compte Minecraft");
            }

            if (Boolean.TRUE.equals(existingLink.getLinked())) {
                throw new ApiBadRequestException("Un lien existe déjà pour cet utilisateur. Veuillez le délier avant d'en créer un nouveau");
            } else {
                throw new ApiBadRequestException("Un lien est en cours de création pour cet utilisateur. Veuillez le délier avant d'en créer un nouveau");
            }
        } else {
            if (this.getRepository().findByMinecraftUuid(newCreation.getMinecraftUuid().toString()).isPresent()) {
                throw new ApiBadRequestException("Un lien existe déjà pour ce compte Minecraft");
            }
        }
    }
}
