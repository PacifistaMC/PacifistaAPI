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
     * le pseudo minecraft de la personne bannie
     */
    @NotBlank(message = "Le pseudo Minecraft de l'utilisateur banni est manquant")
    private String minecraftUserNameBanned;

    /**
     * Le UUID du compte
     */
    @NotNull(message = "L'uuid de l'utilisateur funixproductions est manquant")
    private UUID funixProdUserIdBanned;

    /**
     * Pseudo Minecraft de la personne qui sanctionne le joueur
     */
    @NotNull(message = "Le pseudo du staff qui met le man est manquant")
    private String staffMinecraftUserName;

    /**
     * UUID funix prod account
     */
    @NotNull(message = "UUID du staff manquant")
    private UUID staffFunixProdUserId;

}
