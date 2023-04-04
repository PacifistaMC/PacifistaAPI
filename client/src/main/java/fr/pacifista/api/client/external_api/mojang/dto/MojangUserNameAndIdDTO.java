package fr.pacifista.api.client.external_api.mojang.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MojangUserNameAndIdDTO {
    private String name;
    private String id;

    /**
     * Converts the uuid returned by the api to a valid UUID object instance.
     * @return UUID
     */
    public UUID getId() {
        final StringBuilder buffer = new StringBuilder(id);

        for (int i = 0; i < buffer.length(); ++i) {
            if (i == 8 || i == 13 || i == 18 || i == 23)
                buffer.insert(i, '-');
        }

        return UUID.fromString(buffer.toString());
    }
}
