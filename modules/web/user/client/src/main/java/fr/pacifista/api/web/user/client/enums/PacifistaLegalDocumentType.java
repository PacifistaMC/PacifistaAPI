package fr.pacifista.api.web.user.client.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PacifistaLegalDocumentType {
    /**
     * <p>Les CGV régissent les ventes de produits et services sur ton site.
     * Elles doivent inclure des informations sur les produits/services,
     * les prix, les modalités de paiement, de livraison, de rétractation,
     * les garanties, et les responsabilités.</p>
     */
    CGV("Conditions Générales de Vente"),

    /**
     * <p>
     *     Les CGU définissent les règles d'utilisation de ton site web.
     *     Elles couvrent des aspects comme les droits et obligations des utilisateurs,
     *     les règles de conduite sur le site, la propriété intellectuelle,
     *     et la responsabilité de l'éditeur du site.
     * </p>
     */
    CGU("Conditions Générales d'Utilisation"),

    /**
     * <p>
     *     Cette politique informe les utilisateurs sur la manière dont leurs données personnelles
     *     sont collectées, utilisées, stockées et protégées. Elle doit être conforme au
     *     Règlement Général sur la Protection des Données (RGPD) et inclure des informations
     *     sur les droits des utilisateurs en matière de protection des données.
     * </p>
     */
    PRIVACY_POLICY("Politique de Confidentialité");

    private final String humanName;
}
