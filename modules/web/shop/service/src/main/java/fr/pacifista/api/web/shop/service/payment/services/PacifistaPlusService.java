package fr.pacifista.api.web.shop.service.payment.services;

import com.funixproductions.api.payment.paypal.client.clients.PaypalPlanFeignClient;
import com.funixproductions.api.payment.paypal.client.clients.PaypalSubscriptionFeignClient;
import com.funixproductions.api.payment.paypal.client.dtos.requests.paypal.PaypalCreateSubscriptionDTO;
import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalPlanDTO;
import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalSubscriptionDTO;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.core.service.tools.discord.dtos.DiscordSendMessageWebHookDTO;
import fr.pacifista.api.core.service.tools.discord.services.DiscordMessagesService;
import fr.pacifista.api.server.essentials.client.pacifista_plus.clients.PacifistaPlusSubscriptionClient;
import fr.pacifista.api.server.essentials.client.pacifista_plus.dtos.PacifistaPlusSubscriptionDTO;
import fr.pacifista.api.web.shop.service.configs.ShopConfig;
import fr.pacifista.api.web.user.client.components.PacifistaWebUserLinkComponent;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j(topic = "PacifistaPlusService")
public class PacifistaPlusService {

    public static final String SUBSCRIPTION_NAME = "pacifistaplus-subscription";
    public static final String PROJECT_NAME = "pacifista";

    private static final Double PRICE_HT = 4.16;
    private static final String CANCELLATION_URL = "https://pacifista.fr/shop/pacifista+";

    /**
     * Client pour les liens avec les comptes funixprod et Minecraft
     */
    private final PacifistaWebUserLinkComponent userLinkComponent;

    /**
     * Client pour les abonnements via PayPal
     */
    private final PaypalSubscriptionFeignClient subscriptionClient;

    /**
     * Client pour les plans via PayPal pour les abonnements ensuite
     */
    private final PaypalPlanFeignClient paypalPlanClient;

    /**
     * Client pour les abonnements Pacifista+ sur Pacifista Minecraft
     */
    private final PacifistaPlusSubscriptionClient pacifistaPlusSubscriptionClient;

    /**
     * Plan Pacifista+ sur PayPal
     */
    private PaypalPlanDTO pacifistaPlusPlan;

    /**
     * Service pour envoyer des messages sur Discord
     */
    private final DiscordMessagesService discordMessagesService;

    /**
     * Configuration de la boutique
     */
    private final ShopConfig shopConfig;

    public PacifistaPlusService(PacifistaWebUserLinkComponent pacifistaWebUserLinkComponent,
                                PaypalSubscriptionFeignClient subscriptionClient,
                                PaypalPlanFeignClient paypalPlanClient,
                                PacifistaPlusSubscriptionClient pacifistaPlusSubscriptionClient,
                                DiscordMessagesService discordMessagesService,
                                ShopConfig shopConfig) throws ApiException {
        this.pacifistaPlusSubscriptionClient = pacifistaPlusSubscriptionClient;
        this.discordMessagesService = discordMessagesService;
        this.userLinkComponent = pacifistaWebUserLinkComponent;
        this.subscriptionClient = subscriptionClient;
        this.paypalPlanClient = paypalPlanClient;
        this.shopConfig = shopConfig;
    }

    public void onNewPaypalSubscription(final UUID funixProdUserId, final Date expirationDate) {
        final PacifistaWebUserLinkDTO pacifistaWebUserLink = this.getUserLink(funixProdUserId);
        if (pacifistaWebUserLink == null) return;
        final UUID minecraftUuid = UUID.fromString(pacifistaWebUserLink.getMinecraftUuid());

        PacifistaPlusSubscriptionDTO subscriptionDTO = this.getPacifistaPlusStatus(minecraftUuid);
        if (subscriptionDTO == null) {
            subscriptionDTO = new PacifistaPlusSubscriptionDTO(minecraftUuid, 0, expirationDate);
        }
        subscriptionDTO.setMonths(subscriptionDTO.getMonths() + 1);
        subscriptionDTO.setExpirationDate(expirationDate);

        try {
            if (subscriptionDTO.getId() == null) {
                this.pacifistaPlusSubscriptionClient.create(subscriptionDTO);
            } else {
                this.pacifistaPlusSubscriptionClient.update(subscriptionDTO);
            }
            log.info("New subscription Pacifista+ for user {} with {} cycles and expiration date {}", funixProdUserId, subscriptionDTO.getMonths(), expirationDate);
            this.sendNewSubscriptionToDiscord(pacifistaWebUserLink.getMinecraftUsername(), subscriptionDTO.getMonths());
        } catch (ApiException e) {
            log.error("Erreur lors de la création de l'abonnement Pacifista+ sur Minecraft pour l'utilisateur funixprod id {}.", funixProdUserId, new ApiException(
                    String.format(
                            "Erreur lors de la création de l'abonnement Pacifista+ sur Minecraft pour l'utilisateur funixprod id %s, minecraft uuid: %s et minecraft username: %s.",
                            funixProdUserId,
                            minecraftUuid,
                            pacifistaWebUserLink.getMinecraftUsername()
                    ),
                    e
            ));
        }
    }

    @NonNull
    public PaypalSubscriptionDTO newSubscription(final UUID funixProdUserId) throws ApiException {
        try {
            final PacifistaWebUserLinkDTO pacifistaWebUserLink = this.userLinkComponent.getLink(funixProdUserId);
            final PaypalSubscriptionDTO subscriptionDTO = this.subscriptionClient.subscribe(new PaypalCreateSubscriptionDTO(
                    this.getPlan(),
                    funixProdUserId,
                    CANCELLATION_URL,
                    this.shopConfig.getReturnUrl(),
                    "Pacifista+"
            ));

            log.info("L'utilisateur {} a souscrit à un abonnement Pacifista+.", pacifistaWebUserLink);
            this.sendSubscriptionStatusToDiscord(pacifistaWebUserLink.getMinecraftUsername(), "créé");
            return subscriptionDTO;
        } catch (Exception e) {
            throw new ApiException(String.format("Erreur lors de la création de l'abonnement Pacifista+ pour l'utilisateur id %s.", funixProdUserId), e);
        }
    }

    @NonNull
    public PaypalSubscriptionDTO getSubscription(final UUID funixProdUserId) throws ApiException {
        final PaypalSubscriptionDTO subscription = this.getSubscriptionForUser(funixProdUserId);

        if (subscription == null) {
            throw new ApiNotFoundException("Aucun abonnement Pacifista+ trouvé pour l'utilisateur id " + funixProdUserId + ".");
        }

        return subscription;
    }

    @NonNull
    public PaypalSubscriptionDTO pauseSubscription(final UUID funixProdUserId) throws ApiException {
        PaypalSubscriptionDTO subscription = this.getSubscription(funixProdUserId);

        try {
            subscription = this.subscriptionClient.pauseSubscription(subscription.getId().toString());
            log.info("L'abonnement Pacifista+ de l'utilisateur id {} a été mis en pause.", funixProdUserId);

            final PacifistaWebUserLinkDTO pacifistaWebUserLink = this.getUserLink(funixProdUserId);
            this.sendSubscriptionStatusToDiscord(pacifistaWebUserLink == null ? "N/A" : pacifistaWebUserLink.getMinecraftUsername(), "mis en pause");

            return subscription;
        } catch (Exception e) {
            throw new ApiException(String.format("Erreur lors de la mise en pause de l'abonnement Pacifista+ pour l'utilisateur id %s.", funixProdUserId), e);
        }
    }

    @NonNull
    public PaypalSubscriptionDTO resumeSubscription(final UUID funixProdUserId) throws ApiException {
        PaypalSubscriptionDTO subscription = this.getSubscription(funixProdUserId);

        try {
            subscription = this.subscriptionClient.activateSubscription(subscription.getId().toString());
            log.info("L'abonnement Pacifista+ de l'utilisateur id {} a été repris.", funixProdUserId);

            final PacifistaWebUserLinkDTO pacifistaWebUserLink = this.getUserLink(funixProdUserId);
            this.sendSubscriptionStatusToDiscord(pacifistaWebUserLink == null ? "N/A" : pacifistaWebUserLink.getMinecraftUsername(), "repris");

            return subscription;
        } catch (Exception e) {
            throw new ApiException(String.format("Erreur lors de la reprise de l'abonnement Pacifista+ pour l'utilisateur id %s.", funixProdUserId), e);
        }
    }

    public void cancelSubscription(final UUID funixProdUserId) throws ApiException {
        PaypalSubscriptionDTO subscription = this.getSubscription(funixProdUserId);

        try {
            this.subscriptionClient.cancelSubscription(subscription.getId().toString());
            log.info("L'abonnement Pacifista+ de l'utilisateur id {} a été annulé.", funixProdUserId);

            final PacifistaWebUserLinkDTO pacifistaWebUserLink = this.getUserLink(funixProdUserId);
            this.sendSubscriptionStatusToDiscord(pacifistaWebUserLink == null ? "N/A" : pacifistaWebUserLink.getMinecraftUsername(), "annulé");
        } catch (Exception e) {
            throw new ApiException(String.format("Erreur lors de l'annulation de l'abonnement Pacifista+ pour l'utilisateur id %s.", funixProdUserId), e);
        }
    }

    @NonNull
    public PaypalPlanDTO getPlan() throws ApiException {
        if (this.pacifistaPlusPlan != null) {
            return this.pacifistaPlusPlan;
        }

        try {
            final PaypalPlanDTO plan = this.paypalPlanClient.getAll(
                    "0",
                    "1",
                    String.format(
                            "projectName:%s:%s,name:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            PROJECT_NAME,
                            SearchOperation.EQUALS.getOperation(),
                            SUBSCRIPTION_NAME
                    ),
                    ""
            ).getContent().getFirst();

            log.info("Le Pacifista+ plan existe déjà sur PayPal. Récupération réussie.");
            this.pacifistaPlusPlan = plan;
            return plan;
        } catch (NoSuchElementException e) {
            final PaypalPlanDTO plan = this.createPlan();

            log.info("Le Pacifista+ plan n'existait pas sur PayPal, il a été créé.");
            this.pacifistaPlusPlan = plan;
            return plan;
        } catch (Exception e) {
            throw new ApiException("Erreur lors de l'obtention du plan pacifista+.", e);
        }
    }

    @Nullable
    private PaypalSubscriptionDTO getSubscriptionForUser(final UUID funixProdUserId) throws ApiException {
        try {
            return this.subscriptionClient.getAll(
                    "0",
                    "1",
                    String.format(
                            "funixProdUserId:%s:%s,plan.name:%s:%s,plan.projectName:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            funixProdUserId,
                            SearchOperation.EQUALS.getOperation(),
                            SUBSCRIPTION_NAME,
                            SearchOperation.EQUALS.getOperation(),
                            PROJECT_NAME
                    ),
                    ""
            ).getContent().getFirst();
        } catch (NoSuchElementException e) {
            return null;
        } catch (Exception e) {
            throw new ApiException("Erreur lors de l'obtention de l'abonnement pacifista+.", e);
        }
    }

    @NonNull
    private PaypalPlanDTO createPlan() throws ApiException {
        final PaypalPlanDTO pacifistaPlusPlan = new PaypalPlanDTO(
                SUBSCRIPTION_NAME,
                "Pacifista+ : Abonnement mensuel du serveur Minecraft Pacifista.",
                "https://pacifista.fr/assets/img/pacifista-logo.webp",
                CANCELLATION_URL,
                PRICE_HT,
                PROJECT_NAME
        );

        try {
            return this.paypalPlanClient.create(pacifistaPlusPlan);
        } catch (Exception e) {
            throw new ApiException("Erreur lors de la création du plan pacifista+.", e);
        }
    }

    @Nullable
    private PacifistaWebUserLinkDTO getUserLink(final UUID funixProdUserId) throws ApiException {
        try {
            return this.userLinkComponent.getLink(funixProdUserId);
        } catch (Exception e) {
            log.error("Impossible de récupérer le lien funixproductions pour l'utilisateur id {}.", funixProdUserId, new ApiException("Erreur lors de la récupération du lien funixproductions avec Pacifista pour le webhook Pacifista+ pour l'utilisateur id: " + funixProdUserId + ".", e));
            return null;
        }
    }

    @Nullable
    private PacifistaPlusSubscriptionDTO getPacifistaPlusStatus(final UUID minecraftUuid) throws ApiException {
        try {
            return this.pacifistaPlusSubscriptionClient.getAll(
                    "0",
                    "1",
                    String.format(
                            "playerId:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            minecraftUuid
                    ),
                    ""
            ).getContent().getFirst();
        } catch (NoSuchElementException e) {
            return null;
        } catch (Exception e) {
            throw new ApiException("Erreur lors de l'obtention de l'abonnement pacifista+.", e);
        }
    }

    private void sendNewSubscriptionToDiscord(final String minecraftUsername, final Integer months) {
        final DiscordSendMessageWebHookDTO message = new DiscordSendMessageWebHookDTO();

        message.setContent(String.format(
                ":sparkles: Un abonnement Pacifista+ a été validé et envoyé sur Minecraft pour le joueur %s pour %d mois.",
                minecraftUsername,
                months
        ));

        this.discordMessagesService.sendAlertMessage(message);
    }

    private void sendSubscriptionStatusToDiscord(final String minecraftUsername, final String action) {
        final DiscordSendMessageWebHookDTO message = new DiscordSendMessageWebHookDTO();

        message.setContent(String.format(
                ":sparkles: L'abonnement Pacifista+ de l'utilisateur %s a été %s sur PayPal.",
                minecraftUsername,
                action
        ));

        this.discordMessagesService.sendAlertMessage(message);

    }

}
