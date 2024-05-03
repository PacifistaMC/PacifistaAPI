package fr.pacifista.api.core.client.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import fr.pacifista.api.core.client.enums.ServerType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class LocationDTO extends ApiDTO {
    @NotNull
    private UUID worldUuid;

    @NotNull
    private ServerType serverType;

    @NotNull
    private Double x;

    @NotNull
    private Double y;

    @NotNull
    private Double z;

    @NotNull
    private Float yaw;

    @NotNull
    private Float pitch;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final LocationDTO other) {
            return worldUuid.equals(other.worldUuid) &&
                    serverType.equals(other.serverType) &&
                    x.equals(other.x) &&
                    y.equals(other.y) &&
                    z.equals(other.z) &&
                    yaw.equals(other.yaw) &&
                    pitch.equals(other.pitch) &&
                    super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return worldUuid.hashCode() +
                serverType.hashCode() +
                x.hashCode() +
                y.hashCode() +
                z.hashCode() +
                yaw.hashCode() +
                pitch.hashCode() +
                super.hashCode();
    }
}
