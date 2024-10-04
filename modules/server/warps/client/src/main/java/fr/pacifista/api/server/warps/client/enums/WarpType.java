package fr.pacifista.api.server.warps.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WarpType {
    /**
     * Ce type de warp est utilisé pour les zones publiques. Par exemple une ferme a exp.
     */
    STAFF_WARP("Warp"),

    /**
     * Un warp qu'un joueur a créé et les joueurs peuvent y avoir accès. (Selon config)
     */
    PLAYER_WARP("Warp Joueur"),

    /**
     * Ce type de warp est pour indiquer au staff que le warp doit être validé et vérifié.
     */
    NEED_STAFF_VALIDATION("Warp en cours de validation"),

    /**
     * Ce type de warp est pour indiquer au joueur que le warp a été refusé par le staff.
     */
    STAFF_REFUSED_WARP("Warp refusé par le staff");

    private final String humanReadableName;
}
