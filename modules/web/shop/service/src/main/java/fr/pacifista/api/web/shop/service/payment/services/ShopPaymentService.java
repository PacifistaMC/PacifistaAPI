package fr.pacifista.api.web.shop.service.payment.services;

import com.funixproductions.api.payment.billing.client.enums.PaymentType;
import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalOrderDTO;
import com.funixproductions.api.payment.paypal.client.enums.OrderStatus;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.exceptions.ApiForbiddenException;
import com.funixproductions.core.exceptions.ApiUnauthorizedException;
import fr.pacifista.api.core.service.tools.discord.services.DiscordMessagesService;
import fr.pacifista.api.server.essentials.client.commands_sender.dtos.CommandToSendDTO;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerDataDTO;
import fr.pacifista.api.web.shop.client.payment.clients.ShopPaymentClient;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentRequestDTO;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentResponseDTO;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import fr.pacifista.api.web.shop.service.articles.services.ShopArticleService;
import fr.pacifista.api.web.shop.service.payment.entities.ShopArticlePurchase;
import fr.pacifista.api.web.shop.service.payment.entities.ShopPayment;
import fr.pacifista.api.web.shop.service.payment.mappers.ShopPaymentMapper;
import fr.pacifista.api.web.shop.service.payment.repositories.ShopArticlePurchaseRepository;
import fr.pacifista.api.web.shop.service.payment.repositories.ShopPaymentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ShopPaymentService implements ShopPaymentClient {

    private final ShopPaymentRepository shopPaymentRepository;
    private final ShopPaymentMapper shopPaymentMapper;
    private final ShopArticlePurchaseRepository shopArticlePurchaseRepository;

    private final ShopArticleService shopArticleService;

    private final CurrentSession currentSession;
    private final PaypalPaymentService paypalPaymentService;

    private final CommandCreationService commandCreationService;
    private final FetchPlayerDataService fetchPlayerDataService;

    private final DiscordMessagesService discordMessagesService;

    @Override
    public PacifistaShopPaymentResponseDTO createOrder(PacifistaShopPaymentRequestDTO request) {
        final UserDTO currentUser = this.getCurrentUser();
        final PacifistaPlayerDataDTO playerDataDTO = this.fetchPlayerDataService.getPlayerData(currentUser.getId().toString());

        final Map<ShopArticle, Integer> articles = this.getArticles(request.getArticles());
        final ShopPayment shopPayment = this.createDTO(request, currentUser.getId(), articles);
        final PaypalOrderDTO paypalOrderDTO;

        if (request.getCreditCard() == null) {
            paypalOrderDTO = paypalPaymentService.createOrder(articles, currentUser, shopPayment.getUuid());
        } else {
            paypalOrderDTO = paypalPaymentService.createOrder(articles, currentUser, shopPayment.getUuid(), request.getCreditCard());
        }

        shopPayment.setPaymentExternalOrderId(paypalOrderDTO.getOrderId());

        final PacifistaShopPaymentResponseDTO dto = this.shopPaymentMapper.toDto(this.shopPaymentRepository.save(shopPayment));
        dto.setUrlClientRedirection(paypalOrderDTO.getUrlClientRedirection());
        dto.setOrderPaid(paypalOrderDTO.getStatus() == OrderStatus.COMPLETED);

        if (Boolean.TRUE.equals(dto.getOrderPaid())) {
            this.sendAlertToDiscord(playerDataDTO.getMinecraftUsername(), shopPayment);
        }

        return dto;
    }

    @Override
    public PacifistaShopPaymentResponseDTO getPaymentStatus(String paymentExternalOrderId) {
        final UserDTO currentUser = getCurrentUser();
        final ShopPayment shopPayment = getShopPaymentByUser(paymentExternalOrderId, currentUser);

        if (shopPayment.getPaymentType().equals(PaymentType.PAYPAL) || shopPayment.getPaymentType().equals(PaymentType.CREDIT_CARD)) {
            final PaypalOrderDTO paypalOrderDTO = paypalPaymentService.getOrder(paymentExternalOrderId);
            return new PacifistaShopPaymentResponseDTO(paypalOrderDTO, currentUser);
        } else {
            throw new ApiBadRequestException(String.format("Le type de paiement %s n'est pas supporté.", shopPayment.getPaymentType()));
        }
    }

    @Override
    public PacifistaShopPaymentResponseDTO capturePayment(String paymentExternalOrderId) {
        final UserDTO currentUser = getCurrentUser();
        final PacifistaPlayerDataDTO playerDataDTO = this.fetchPlayerDataService.getPlayerData(currentUser.getId().toString());
        final ShopPayment shopPayment = getShopPaymentByUser(paymentExternalOrderId, currentUser);

        if (shopPayment.getPaymentType().equals(PaymentType.PAYPAL) || shopPayment.getPaymentType().equals(PaymentType.CREDIT_CARD)) {
            final List<CommandToSendDTO> commandsCreated = commandCreationService.sendCommandCreation(shopPayment);

            try {
                final PaypalOrderDTO paypalOrderDTO = paypalPaymentService.captureOrder(paymentExternalOrderId);
                this.sendAlertToDiscord(playerDataDTO.getMinecraftUsername(), shopPayment);
                return new PacifistaShopPaymentResponseDTO(paypalOrderDTO, currentUser);
            } catch (ApiException e) {
                commandCreationService.rollbackCreation(commandsCreated);
                throw e;
            }
        } else {
            throw new ApiBadRequestException(String.format("Le type de paiement %s n'est pas supporté.", shopPayment.getPaymentType()));
        }
    }

    private UserDTO getCurrentUser() {
        final UserDTO currentUser = currentSession.getCurrentUser();

        if (currentUser == null) {
            throw new ApiUnauthorizedException("Vous devez être connecté pour effectuer un paiement.");
        } else {
            return currentUser;
        }
    }

    private Map<ShopArticle, Integer> getArticles(final List<PacifistaShopPaymentRequestDTO.ShopArticleRequest> articles) {
        final Map<ShopArticle, Integer> articlesMap = new HashMap<>();

        final List<String> articlesIds = new ArrayList<>();
        for (final PacifistaShopPaymentRequestDTO.ShopArticleRequest article : articles) {
            articlesIds.add(article.getArticleId());
        }

        for (final ShopArticle article : this.shopArticleService.getRepository().findAllByUuidIn(articlesIds)) {
            for (final PacifistaShopPaymentRequestDTO.ShopArticleRequest articleRequest : articles) {
                if (articleRequest.getArticleId().equals(article.getUuid().toString())) {
                    articlesMap.put(article, articleRequest.getQuantity());
                }
            }
        }

        return articlesMap;
    }

    private ShopPayment getShopPaymentByUser(final String paymentExternalOrderId, final UserDTO currentUser) {
        return this.shopPaymentRepository.findByPaymentExternalOrderIdAndUserId(paymentExternalOrderId, currentUser.getId().toString()).orElseThrow(() -> new ApiForbiddenException("Vous n'êtes pas autorisé à accéder à ce paiement."));
    }

    private ShopPayment createDTO(@NonNull final PacifistaShopPaymentRequestDTO request,
                                  @NonNull final UUID userId,
                                  @NonNull final Map<ShopArticle, Integer> articles) {
        try {
            ShopPayment shopPayment = new ShopPayment();

            shopPayment.setPaymentType(determinePaymentTypeFromRequest(request));
            shopPayment.setUserId(userId.toString());
            shopPayment = this.shopPaymentRepository.save(shopPayment);

            final Set<ShopArticlePurchase> shopArticlePurchases = new HashSet<>();
            for (final PacifistaShopPaymentRequestDTO.ShopArticleRequest articleRequest : request.getArticles()) {
                final ShopArticlePurchase shopArticlePurchase = new ShopArticlePurchase();

                shopArticlePurchase.setPayment(shopPayment);
                shopArticlePurchase.setQuantity(articleRequest.getQuantity());
                shopArticlePurchase.setArticle(getArticleFromMap(articles, articleRequest.getArticleId()));

                shopArticlePurchases.add(shopArticlePurchase);
            }
            this.shopArticlePurchaseRepository.saveAll(shopArticlePurchases);
            return shopPayment;
        } catch (Exception e) {
            throw new ApiException("Impossible de créer le paiement. Erreur lors de la création du paiement.", e);
        }
    }

    private ShopArticle getArticleFromMap(final Map<ShopArticle, Integer> articles, final String articleId) {
        for (final Map.Entry<ShopArticle, Integer> entry : articles.entrySet()) {
            final ShopArticle article = entry.getKey();

            if (article.getUuid().toString().equals(articleId)) {
                return article;
            }
        }
        throw new ApiBadRequestException(String.format("L'article %s n'existe pas.", articleId));
    }

    private PaymentType determinePaymentTypeFromRequest(@NonNull final PacifistaShopPaymentRequestDTO request) {
        if (request.getCreditCard() == null) {
            return PaymentType.PAYPAL;
        } else {
            return PaymentType.CREDIT_CARD;
        }
    }

    private void sendAlertToDiscord(final String minecraftUsername, final ShopPayment shopPayment) {
        final Discord
        this.discordMessagesService.sendAlertMessage();
    }

}
