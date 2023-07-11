package fr.pacifista.api.server.permissions.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
