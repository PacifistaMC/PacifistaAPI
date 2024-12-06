package fr.pacifista.api.core.service.tools.discord.dtos.embeds;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmbedImage {

    /**
     * source url of image (only supports http(s) and attachments)
     */
    private String url;

    /**
     * height of image
     */
    private Integer height;

    /**
     * width of image
     */
    private Integer width;

    public EmbedImage(String url) {
        this.url = url;
    }

}
