package fr.pacifista.api.web.news.client.dtos.ban;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacifistaNewsBanDTO extends ApiDTO {

    /**
     * La raison du ban
     */
    private String reason;

    /**
     * Le pseudo minecraft de la personne bannie
     */
    @NotBlank(message = "Le pseudo minecraft de l'utilisateur banni est manquant")
    private String minecraftUserNameBanned;

    /**
     * Le UUID du compte
     */
    @NotNull(message = "L'uuid de l'utilisateur funixproductions est manquant")
    private UUID funixProdUserIdBanned;

    /**
     * Pseudo Minecraft de la personne qui sanctionne le joueur
     */
    private String staffMinecraftUserName;

    /**
     * UUID funix prod account
     */
    private UUID staffFunixProdUserId;

    public PacifistaNewsBanDTO(final UUID funixProdUserIdBanned,
                               final String minecraftUserNameBanned) {
        this.funixProdUserIdBanned = funixProdUserIdBanned;
        this.minecraftUserNameBanned = minecraftUserNameBanned;
    }

    public PacifistaNewsBanDTO(final UUID funixProdUserIdBanned,
                               final String minecraftUserNameBanned,
                               final String reason) {
        this.funixProdUserIdBanned = funixProdUserIdBanned;
        this.minecraftUserNameBanned = minecraftUserNameBanned;
        this.reason = reason;
    }

}
