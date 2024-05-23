package fr.pacifista.api.core.client.dtos;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final MinecraftPlayerDTO other) {
            return super.equals(obj) &&
                    (minecraftUuid != null && other.minecraftUuid != null && minecraftUuid.equals(other.minecraftUuid)) &&
                    (minecraftUsername != null && other.minecraftUsername != null && minecraftUsername.equals(other.minecraftUsername));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 13 +
            (minecraftUuid != null ? minecraftUuid.hashCode() : 0) +
            (minecraftUsername != null ? minecraftUsername.hashCode() : 0);
    }
}
