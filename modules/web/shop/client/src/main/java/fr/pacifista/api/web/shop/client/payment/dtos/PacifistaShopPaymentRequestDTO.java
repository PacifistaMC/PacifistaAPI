package fr.pacifista.api.web.shop.client.payment.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacifistaShopPaymentRequestDTO {

    /**
     * Liste des id des articles à payer
     */
    @Valid
    @NotNull(message = "La liste des id des articles à payer ne peut pas être nulle")
    @NotEmpty(message = "La liste des id des articles à payer ne peut pas être vide")
    private List<@Valid ShopArticleRequest> articles;

    /**
     * Nullable, si null alors le paiement se fera avec paypal checkout
     */
    @Valid
    private CreditCard creditCard;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShopArticleRequest {

        @NotBlank(message = "L'id de l'article est obligatoire")
        private String articleId;

        @NotNull(message = "La quantité de l'article est obligatoire")
        @Min(value = 1, message = "La quantité de l'article est invalide")
        private Integer quantity;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreditCard {
        @NotBlank(message = "Le nom du titulaire de la carte est obligatoire")
        @Size(min = 1, max = 300, message = "Le nom du titulaire de la carte est invalide")
        private String cardHolderName;

        @NotBlank(message = "Le numéro de la carte est obligatoire")
        @Size(min = 13, max = 19, message = "Le numéro de la carte est invalide")
        private String cardNumber;

        @NotBlank(message = "Le code de sécurité de la carte est obligatoire")
        @Size(min = 3, max = 4, message = "Le code de sécurité de la carte est invalide")
        private String securityCode;

        @NotNull(message = "Le mois d'expiration de la carte est obligatoire")
        @Max(value = 12, message = "Le mois d'expiration de la carte est invalide")
        @Min(value = 1, message = "Le mois d'expiration de la carte est invalide")
        private Integer expirationMonth;

        @NotNull(message = "L'année d'expiration de la carte est obligatoire")
        @Max(value = 2200, message = "L'année d'expiration de la carte est invalide")
        @Min(value = 2024, message = "L'année d'expiration de la carte est invalide")
        private Integer expirationYear;
    }

}
