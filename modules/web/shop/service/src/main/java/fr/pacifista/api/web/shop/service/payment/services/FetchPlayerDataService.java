package fr.pacifista.api.web.shop.service.payment.services;

import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.players.data.client.clients.PacifistaPlayerDataInternalClient;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerDataDTO;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkInternalClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FetchPlayerDataService {

    private final PacifistaWebUserLinkInternalClient userLinkClient;
    private final PacifistaPlayerDataInternalClient playerDataClient;

    @NonNull
    protected PacifistaPlayerDataDTO getPlayerData(final String userId) throws ApiException {
        final PacifistaWebUserLinkDTO linkedMinecraftAccount = this.getLinkedMinecraftAccount(userId);

        try {
            return this.playerDataClient.getAll(
                    "0",
                    "1",
                    String.format(
                            "minecraftUuid:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            linkedMinecraftAccount.getMinecraftUuid()
                    ),
                    ""
            ).getContent().getFirst();
        } catch (ApiException e) {
            throw e;
        } catch (NoSuchElementException e) {
            throw new ApiNotFoundException("Le compte Minecraft lié au compte funixproductions ne s'est jamais connecté sur Pacifista.");
        } catch (Exception e) {
            throw new ApiException("Impossible de récupérer les données du joueur.", e);
        }
    }

    private PacifistaWebUserLinkDTO getLinkedMinecraftAccount(final String userId) throws ApiException {
        try {
            final PacifistaWebUserLinkDTO webUserLinkDTO = this.userLinkClient.getAll(
                    "0",
                    "1",
                    String.format(
                            "funixProdUserId:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            userId
                    ),
                    ""
            ).getContent().getFirst();

            if (Boolean.TRUE.equals(webUserLinkDTO.getLinked())) {
                return webUserLinkDTO;
            } else {
                throw new ApiBadRequestException("Le compte Minecraft n'a pas encore été validé.");
            }
        } catch (ApiException e) {
            throw e;
        } catch (NoSuchElementException e) {
            throw new ApiNotFoundException("Le compte funixproductions n'est lié à aucun compte Minecraft.");
        } catch (Exception e) {
            throw new ApiException("Impossible de récupérer le lien entre le compte funixproductions et le compte Minecraft de l'utilisateur id : " + userId + ".", e);
        }
    }

}
