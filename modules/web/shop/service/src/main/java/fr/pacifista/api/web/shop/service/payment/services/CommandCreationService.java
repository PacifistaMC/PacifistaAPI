package fr.pacifista.api.web.shop.service.payment.services;

import com.funixproductions.core.exceptions.ApiException;
import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.server.essentials.client.commands_sender.clients.CommandToSendInternalClient;
import fr.pacifista.api.server.essentials.client.commands_sender.dtos.CommandToSendDTO;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerDataDTO;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import fr.pacifista.api.web.shop.service.payment.entities.ShopArticlePurchase;
import fr.pacifista.api.web.shop.service.payment.entities.ShopPayment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "ShopPaymentService")
@Service
@RequiredArgsConstructor
public class CommandCreationService {

    private final CommandToSendInternalClient commandSenderClient;
    private final FetchPlayerDataService playerDataService;

    protected List<CommandToSendDTO> sendCommandCreation(final ShopPayment shopPayment) throws ApiException {
        final List<CommandToSendDTO> toSend = new ArrayList<>();

        ShopArticle shopArticle;
        for (final ShopArticlePurchase purchase : shopPayment.getPurchases()) {
            shopArticle = purchase.getArticle();
            toSend.add(new CommandToSendDTO(
                    getCommandExecutedAndReplaceArgs(purchase),
                    shopArticle.getServerType() == null ? ServerType.SURVIE_ALPHA : shopArticle.getServerType(),
                    shopArticle.getServerType() == null,
                    "pacifista-api-shop"
            ));
        }

        try {
            return this.commandSenderClient.create(toSend);
        } catch (Exception e) {
            throw new ApiException("Impossible de créer les commandes liés aux achats.", e);
        }
    }

    protected void rollbackCreation(final List<CommandToSendDTO> commands) {
        final List<String> commandsCreatedIds = new ArrayList<>();

        for (final CommandToSendDTO commandToSendDTO : commands) {
            commandsCreatedIds.add(commandToSendDTO.getId().toString());
        }

        try {
            this.commandSenderClient.delete(commandsCreatedIds.toArray(new String[0]));
        } catch (Exception e) {
            log.error("Impossible de supprimer les commandes créées.", new ApiException("Impossible de supprimer les commandes créées.", e));
        }
    }

    private String getCommandExecutedAndReplaceArgs(final ShopArticlePurchase purchase) throws ApiException {
        final PacifistaPlayerDataDTO playerData = this.playerDataService.getPlayerData(purchase.getPayment().getUserId());
        String commandExecuted = purchase.getArticle().getCommandExecuted();

        commandExecuted = commandExecuted.replace("{QUANTITY}", purchase.getQuantity().toString());
        commandExecuted = commandExecuted.replace("{PLAYER_UUID}", playerData.getMinecraftUuid().toString());
        commandExecuted = commandExecuted.replace("{PLAYER_NAME}", playerData.getMinecraftUsername());
        return commandExecuted;
    }



}
