package fr.pacifista.api.web.user.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Cette classe est utilisée pour lier un compte funixproductions avec un compte Minecraft
 */
@Getter
@Setter
@NoArgsConstructor
public class PacifistaWebUserLinkDTO extends ApiDTO {

    @NotNull(message = "Le champ funixProdUserId est obligatoire")
    private UUID funixProdUserId;

    @NotNull(message = "Le champ minecraftUuid est obligatoire")
    private UUID minecraftUuid;

    /**
     * Indique si le compte est lié ou non
     */
    private Boolean linked;

    /**
     * Générée par l'api, permet de valider un compte avec.
     */
    private String linkKey;

    public String getFunixProdUserId() {
        if (funixProdUserId == null) {
            return null;
        } else {
            return funixProdUserId.toString();
        }
    }

    public String getMinecraftUuid() {
        if (minecraftUuid == null) {
            return null;
        } else {
            return minecraftUuid.toString();
        }
    }

    public void setFunixProdUserId(String funixProdUserId) {
        if (funixProdUserId == null) {
            this.funixProdUserId = null;
        } else {
            this.funixProdUserId = UUID.fromString(funixProdUserId);
        }
    }

    public void setMinecraftUuid(String minecraftUuid) {
        if (minecraftUuid == null) {
            this.minecraftUuid = null;
        } else {
            this.minecraftUuid = UUID.fromString(minecraftUuid);
        }
    }

    /**
     * Constructeur pour la création d'un lien entre un compte funixproductions et un compte Minecraft
     * @param funixProdUserId funixprod api id
     * @param minecraftUuid mojang user id
     */
    public PacifistaWebUserLinkDTO(final UUID funixProdUserId,
                                   final UUID minecraftUuid) {
        this.funixProdUserId = funixProdUserId;
        this.minecraftUuid = minecraftUuid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final PacifistaWebUserLinkDTO other) {
            return funixProdUserId.equals(other.funixProdUserId) &&
                    minecraftUuid.equals(other.minecraftUuid) &&
                    linked.equals(other.linked) &&
                    super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() +
                (funixProdUserId != null ? funixProdUserId.hashCode() : 0) +
                (minecraftUuid != null ? minecraftUuid.hashCode() : 0) +
                (linked != null ? linked.hashCode() : 0) + 13;
    }
}
