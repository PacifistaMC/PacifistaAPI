package fr.pacifista.api.core.client.enums.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


public abstract class MinecraftPlayerDTO extends ApiDTO {

    /**
     * The player's Minecraft UUID.
     */
    @Getter
    @Setter
    @NotNull
    private UUID minecraftUuid;

    /**
     * The player's username.
     */
    @NotBlank
    private String minecraftUsername;

    public void setMinecraftUsername(String minecraftUsername) {
        if (minecraftUsername == null) {
            this.minecraftUsername = null;
        } else {
            this.minecraftUsername = minecraftUsername.toLowerCase();
        }
    }

    public String getMinecraftUsername() {
        if (minecraftUsername == null) {
            return null;
        } else {
            return minecraftUsername.toLowerCase();
        }
    }
}
