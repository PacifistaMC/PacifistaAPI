package fr.pacifista.api.web.shop.service.payment.services;

import com.funixproductions.api.payment.billing.client.enums.PaymentType;
import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalOrderDTO;
import com.funixproductions.api.payment.paypal.client.enums.OrderStatus;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiUnauthorizedException;
import fr.pacifista.api.web.shop.client.payment.clients.ShopPaymentClient;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentRequestDTO;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentResponseDTO;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import fr.pacifista.api.web.shop.service.articles.services.ShopArticleService;
import fr.pacifista.api.web.shop.service.payment.entities.ShopPayment;
import fr.pacifista.api.web.shop.service.payment.mappers.ShopPaymentMapper;
import fr.pacifista.api.web.shop.service.payment.repositories.ShopPaymentRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopPaymentCrudService extends ApiService<PacifistaShopPaymentResponseDTO, ShopPayment, ShopPaymentMapper, ShopPaymentRepository> implements ShopPaymentClient {

    private final ShopArticleService shopArticleService;

    private final CurrentSession currentSession;
    private final PaypalPaymentService paypalPaymentService;

    public ShopPaymentCrudService(ShopPaymentRepository repository,
                                  ShopPaymentMapper mapper,
                                  PaypalPaymentService paypalPaymentService,
                                  ShopArticleService shopArticleService,
                                  CurrentSession currentSession) {
        super(repository, mapper);
        this.paypalPaymentService = paypalPaymentService;
        this.currentSession = currentSession;
        this.shopArticleService = shopArticleService;
    }

    @Override
    public PacifistaShopPaymentResponseDTO createOrder(PacifistaShopPaymentRequestDTO request) {
        final UserDTO currentUser = getCurrentUser();
        final PacifistaShopPaymentResponseDTO dto = createDTO(request, currentUser.getId());
        final Map<ShopArticle, Integer> articles = getArticles(request.getArticles());
        final PaypalOrderDTO paypalOrderDTO;

        if (request.getCreditCard() == null) {
            paypalOrderDTO = paypalPaymentService.createOrder(articles, currentUser, dto.getId());
        } else {
            paypalOrderDTO = paypalPaymentService.createOrder(articles, currentUser, dto.getId(), request.getCreditCard());
        }

        dto.setPaymentExternalOrderId(paypalOrderDTO.getOrderId());
        dto.setUrlClientRedirection(paypalOrderDTO.getUrlClientRedirection());
        dto.setOrderPaid(paypalOrderDTO.getStatus() == OrderStatus.COMPLETED);
        return super.update(dto);
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
        final ShopPayment shopPayment = getShopPaymentByUser(paymentExternalOrderId, currentUser);

        if (shopPayment.getPaymentType().equals(PaymentType.PAYPAL) || shopPayment.getPaymentType().equals(PaymentType.CREDIT_CARD)) {
            final PaypalOrderDTO paypalOrderDTO = paypalPaymentService.captureOrder(paymentExternalOrderId);
            return new PacifistaShopPaymentResponseDTO(paypalOrderDTO, currentUser);
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

        final Iterable<ShopArticle> articlesDb = this.shopArticleService.getRepository().findAllByUuidIn(articlesIds);
        for (final ShopArticle article : articlesDb) {
            for (final PacifistaShopPaymentRequestDTO.ShopArticleRequest articleRequest : articles) {
                if (articleRequest.getArticleId().equals(article.getUuid().toString())) {
                    articlesMap.put(article, articleRequest.getQuantity());
                }
            }
        }

        return articlesMap;
    }

    private ShopPayment getShopPaymentByUser(final String paymentExternalOrderId, final UserDTO currentUser) {
        return getRepository().findByPaymentExternalOrderIdAndUserId(paymentExternalOrderId, currentUser.getId().toString()).orElseThrow(() -> new ApiUnauthorizedException("Vous n'êtes pas autorisé à accéder à ce paiement."));
    }

    private PacifistaShopPaymentResponseDTO createDTO(@NonNull final PacifistaShopPaymentRequestDTO request, @NonNull final UUID userId) {
        final PacifistaShopPaymentResponseDTO dto = new PacifistaShopPaymentResponseDTO();

        dto.setPaymentType(determinePaymentTypeFromRequest(request));
        dto.setUserId(userId.toString());
        return super.create(dto);
    }

    private PaymentType determinePaymentTypeFromRequest(@NonNull final PacifistaShopPaymentRequestDTO request) {
        if (request.getCreditCard() == null) {
            return PaymentType.PAYPAL;
        } else {
            return PaymentType.CREDIT_CARD;
        }
    }

}
