package fr.pacifista.api.web.user.service.services;

import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import com.funixproductions.core.tools.string.PasswordGenerator;
import fr.pacifista.api.server.players.data.client.clients.PacifistaPlayerDataInternalClient;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerDataDTO;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import fr.pacifista.api.web.user.service.entities.PacifistaWebUserLink;
import fr.pacifista.api.web.user.service.mappers.PacifistaWebUserLinkMapper;
import fr.pacifista.api.web.user.service.repositories.PacifistaWebUserLinkRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PacifistaWebUserLinkService extends ApiService<PacifistaWebUserLinkDTO, PacifistaWebUserLink, PacifistaWebUserLinkMapper, PacifistaWebUserLinkRepository> {

    private final PasswordGenerator passwordGenerator;
    private final PacifistaPlayerDataInternalClient pacifistaPlayerDataInternalClient;

    public PacifistaWebUserLinkService(PacifistaWebUserLinkRepository repository,
                                       PacifistaWebUserLinkMapper mapper,
                                       PacifistaPlayerDataInternalClient pacifistaPlayerDataInternalClient) {
        super(repository, mapper);
        this.passwordGenerator = new PasswordGenerator();
        this.passwordGenerator.setAlphaDown(4);
        this.passwordGenerator.setAlphaUpper(4);
        this.passwordGenerator.setNumbersAmount(3);
        this.passwordGenerator.setSpecialCharsAmount(2);
        this.pacifistaPlayerDataInternalClient = pacifistaPlayerDataInternalClient;
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

    @Override
    public void beforeSendingDTO(@NonNull Iterable<PacifistaWebUserLinkDTO> data) {
        final List<String> playerIds = new ArrayList<>();
        for (final PacifistaWebUserLinkDTO dto : data) {
            playerIds.add(dto.getMinecraftUuid());
        }
        final List<PacifistaPlayerDataDTO> playerDataDTOS = getPlayersData(playerIds);

        UUID minecraftUuid;
        for (final PacifistaWebUserLinkDTO dto : data) {
            if (dto.getMinecraftUuid() != null) {
                minecraftUuid = UUID.fromString(dto.getMinecraftUuid());

                for (final PacifistaPlayerDataDTO playerData : playerDataDTOS) {
                    if (minecraftUuid.equals(playerData.getMinecraftUuid())) {
                        dto.setMinecraftUsername(playerData.getMinecraftUsername());
                        break;
                    }
                }
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
            final PacifistaWebUserLinkDTO dto = this.getMapper().toDto(existing.get());

            this.beforeSendingDTO(List.of(dto));
            return dto;
        }
    }

    public UUID getMinecraftUserId(final String username) throws ApiException {
        try {
            final List<PacifistaPlayerDataDTO> data = this.pacifistaPlayerDataInternalClient.getAll(
                    "0",
                    "1",
                    String.format(
                            "minecraftUsername:%s:%s",
                            SearchOperation.EQUALS_IGNORE_CASE.getOperation(),
                            username.toLowerCase()
                    ),
                    ""
            ).getContent();

            if (data.isEmpty()) {
                throw new ApiNotFoundException(String.format("Le joueur minecraft %s se s'est jamais connecté sur Pacifista.", username));
            } else {
                return data.get(0).getMinecraftUuid();
            }
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException("Erreur interne, impossible de récupérer l'identifiant du joueur minecraft.", e);
        }
    }

    private List<PacifistaPlayerDataDTO> getPlayersData(final List<String> minecraftUuids) {
        try {
            return this.pacifistaPlayerDataInternalClient.getAll(
                    "0",
                    "300",
                    String.format(
                            "minecraftUuid:%s:[%s]",
                            SearchOperation.EQUALS.getOperation(),
                            String.join("|", minecraftUuids)
                    ),
                    ""
            ).getContent();
        } catch (Exception e) {
            throw new ApiException("Erreur interne, impossible de récupérer les données des joueurs minecraft.", e);
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
