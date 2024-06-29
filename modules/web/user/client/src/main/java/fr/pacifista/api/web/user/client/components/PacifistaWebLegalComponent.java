package fr.pacifista.api.web.user.client.components;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.web.user.client.clients.PacifistaWebLegalClient;
import fr.pacifista.api.web.user.client.clients.PacifistaWebLegalUserClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalDTO;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalUserDTO;
import fr.pacifista.api.web.user.client.enums.PacifistaLegalDocumentType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Collection;

@Component
@AllArgsConstructor
public class PacifistaWebLegalComponent {

    private final PacifistaWebLegalUserClient pacifistaWebLegalUserClient;
    private final PacifistaWebLegalClient pacifistaWebLegalClient;

    /**
     * Permet de savoir si l'utilisateur a accepté un document légal
     * @param user Utilisateur
     * @param type Type de document légal
     * @throws ApiException en cas d'erreur fatale
     * @throws ApiNotFoundException en cas de document légal non trouvé
     * @throws ApiBadRequestException si l'utilisateur n'a pas accepté le document légal
     */
    public void didUserAcceptLegalDocument(final UserDTO user,
                                           final PacifistaLegalDocumentType type) throws ApiException {
        final Iterable<PacifistaWebLegalDTO> legalDocuments = getLegalDocument(type);
        final Iterable<PacifistaWebLegalUserDTO> legalAcceptedByUser = getLegalAcceptedByUser(user);

        handleDocumentAcceptByUser(legalDocuments, legalAcceptedByUser);
    }

    /**
     * Permet de savoir si l'utilisateur a accepté TOUS les documents légaux
     * @param user Utilisateur
     * @throws ApiException en cas d'erreur fatale
     * @throws ApiNotFoundException en cas de document légal non trouvé
     * @throws ApiBadRequestException si l'utilisateur n'a pas accepté le document légal
     */
    public void didUserAcceptLegalDocument(final UserDTO user) throws ApiException {
        final Iterable<PacifistaWebLegalDTO> legalDocuments = getLegalDocument(null);
        final Iterable<PacifistaWebLegalUserDTO> legalAcceptedByUser = getLegalAcceptedByUser(user);

        handleDocumentAcceptByUser(legalDocuments, legalAcceptedByUser);
    }

    private Iterable<PacifistaWebLegalUserDTO> getLegalAcceptedByUser(final UserDTO userDTO) throws ApiException {
        try {
            return pacifistaWebLegalUserClient.getAll("0", "300", String.format(
                    "userId:%s:%s",
                    SearchOperation.EQUALS.getOperation(),
                    userDTO.getId().toString()
            ), "").getContent();
        } catch (Exception e) {
            throw new ApiException("Une erreur est survenue lors de la récupération des documents légaux acceptés par l'utilisateur.", e);
        }
    }

    private Iterable<PacifistaWebLegalDTO> getLegalDocument(final @Nullable PacifistaLegalDocumentType type) throws ApiException {
        try {
            final Collection<PacifistaWebLegalDTO> legalDocuments;

            if (type == null) {
                legalDocuments = pacifistaWebLegalClient.getAll("0", "300", "", "").getContent();
            } else {
                legalDocuments = pacifistaWebLegalClient.getAll("0", "300", String.format(
                        "type:%s:%s",
                        SearchOperation.EQUALS.getOperation(),
                        type.name()
                ), "").getContent();
            }

            if (legalDocuments.isEmpty()) {
                throw new ApiNotFoundException("Aucun document légal trouvé.");
            } else {
                return legalDocuments;
            }
        } catch (Exception e) {
            throw new ApiException("Une erreur est survenue lors de la récupération des documents légaux", e);
        }
    }

    private static void handleDocumentAcceptByUser(Iterable<PacifistaWebLegalDTO> legalDocuments, Iterable<PacifistaWebLegalUserDTO> legalAcceptedByUser) {
        Instant acceptedAt;
        Instant documentLastUpdatedAt;
        PacifistaWebLegalUserDTO legalUser;

        for (final PacifistaWebLegalDTO document : legalDocuments) {
            legalUser = getDocumentAcceptedByUserFromDocumentList(legalAcceptedByUser, document);

            if (legalUser == null) {
                throw new ApiBadRequestException("L'utilisateur n'a pas accepté le document légal : " + document.getType().getHumanName() + ".");
            }
            if (legalUser.getUpdatedAt() != null) {
                acceptedAt = legalUser.getUpdatedAt().toInstant();
            } else {
                acceptedAt = legalUser.getCreatedAt().toInstant();
            }

            if (document.getUpdatedAt() != null) {
                documentLastUpdatedAt = document.getUpdatedAt().toInstant();
            } else {
                documentLastUpdatedAt = document.getCreatedAt().toInstant();
            }

            if (acceptedAt.isBefore(documentLastUpdatedAt)) {
                throw new ApiBadRequestException("L'utilisateur n'a pas accepté la dernière version du document légal : " + document.getType().getHumanName() + ".");
            }
        }
    }

    @Nullable
    private static PacifistaWebLegalUserDTO getDocumentAcceptedByUserFromDocumentList(final Iterable<PacifistaWebLegalUserDTO> legalAcceptedByUser,
                                                                               final PacifistaWebLegalDTO document) {
        for (final PacifistaWebLegalUserDTO legalUser : legalAcceptedByUser) {
            if (legalUser.getType().equals(document.getType())) {
                return legalUser;
            }
        }

        return null;
    }
}
