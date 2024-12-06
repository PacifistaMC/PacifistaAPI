package fr.pacifista.api.core.service.tools.discord.dtos.embeds;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmbedField {

    /**
     * name of the field
     */
    private String name;

    /**
     * value of the field
     */
    private String value;

    /**
     * whether or not this field should display inline
     */
    private Boolean inline;

    public EmbedField(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
