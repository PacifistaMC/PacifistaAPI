package fr.pacifista.api.core.service.tools.discord.dtos.embeds;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscordEmbed {

    /**
     * title of embed
     */
    private String title;

    /**
     * type of embed (always "rich" for webhook embeds)
     */
    private String type = "rich";

    /**
     * description of embed
     */
    private String description;

    /**
     * url of embed
     */
    private String url;

    /**
     * color code of the embed
     */
    private Integer color;

    /**
     * footer information
     */
    private EmbedFooter footer;

    /**
     * image information
     */
    private EmbedImage image;

    /**
     * thumbnail information
     */
    private EmbedImage thumbnail;

    /**
     * fields information, max of 25
     */
    private List<EmbedField> fields;

}