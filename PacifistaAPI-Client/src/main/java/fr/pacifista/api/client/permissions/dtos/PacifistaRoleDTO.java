package fr.pacifista.api.client.permissions.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Role used in game server
 */
@Getter
@Setter
public class PacifistaRoleDTO extends ApiDTO {
    /**
     * Name of the role
     */
    @NotBlank
    private String name;

    /**
     * color in chat the playername color
     * bungee ChatColor
     */
    @NotBlank
    private String playerNameColor;

    /**
     * Prefix before the playerName example : §c§l[Fondateur] §e§l
     */
    @NotBlank
    private String prefix;

    /**
     * Power used in sorting tablist
     * a b c d e f g
     */
    @NotBlank
    private String power;

    /**
     * Staff rank or not
     * used to cumulate for example a lengendary grade with a moderator grade
     */
    @NotNull
    private Boolean staffRank;

    /**
     * List of game permissions
     */
    private List<PacifistaPermissionDTO> permissions;
}
