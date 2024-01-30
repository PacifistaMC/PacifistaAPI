package fr.pacifista.api.web.shop.service.payment.services;

import com.funixproductions.api.payment.billing.client.enums.PaymentType;
import com.funixproductions.api.payment.paypal.client.clients.PaypalOrderFeignClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiUnauthorizedException;
import fr.pacifista.api.web.shop.client.payment.clients.ShopPaymentClient;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentRequestDTO;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentResponseDTO;
import fr.pacifista.api.web.shop.service.payment.entities.ShopPayment;
import fr.pacifista.api.web.shop.service.payment.mappers.ShopPaymentMapper;
import fr.pacifista.api.web.shop.service.payment.repositories.ShopPaymentRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShopPaymentCrudService extends ApiService<PacifistaShopPaymentResponseDTO, ShopPayment, ShopPaymentMapper, ShopPaymentRepository> implements ShopPaymentClient {

    private final PaypalOrderFeignClient paypalOrderFeignClient;
    private final CurrentSession currentSession;

    public ShopPaymentCrudService(ShopPaymentRepository repository,
                                  ShopPaymentMapper mapper,
                                  PaypalOrderFeignClient paypalOrderFeignClient,
                                  CurrentSession currentSession) {
        super(repository, mapper);
        this.paypalOrderFeignClient = paypalOrderFeignClient;
        this.currentSession = currentSession;
    }

    @Override
    public PacifistaShopPaymentResponseDTO createOrder(PacifistaShopPaymentRequestDTO request) {
        final UserDTO currentUser = getCurrentUser();
        final PacifistaShopPaymentResponseDTO dto = createDTO(request, currentUser.getId());

    }

    @Override
    public PacifistaShopPaymentResponseDTO getPaymentStatus(String paymentExternalOrderId) {
        final UserDTO currentUser = getCurrentUser();

    }

    @Override
    public PacifistaShopPaymentResponseDTO capturePayment(String paymentExternalOrderId) {
        final UserDTO currentUser = getCurrentUser();

    }

    private UserDTO getCurrentUser() {
        final UserDTO currentUser = currentSession.getCurrentUser();

        if (currentUser == null) {
            throw new ApiUnauthorizedException("Vous devez être connecté pour effectuer un paiement.");
        } else {
            return currentUser;
        }
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
