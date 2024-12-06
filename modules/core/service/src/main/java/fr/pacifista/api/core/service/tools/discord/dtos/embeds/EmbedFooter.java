package fr.pacifista.api.core.service.tools.discord.dtos.embeds;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmbedFooter {

    /**
     * footer text
     */
    private String text;

    /**
     * url of footer icon (only supports http(s) and attachments)
     */
    @JsonProperty("icon_url")
    private String iconUrl;

    public EmbedFooter(String text) {
        this.text = text;
    }

}
