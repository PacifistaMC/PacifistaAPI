package fr.pacifista.api.core.service.tools.discord.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.pacifista.api.core.service.tools.discord.dtos.embeds.DiscordEmbed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscordSendMessageWebHookDTO {

    /**
     * the message contents (up to 2000 characters)
     */
    private String content;

    /**
     * override the default username of the webhook
     */
    private String username;

    /**
     * override the default avatar of the webhook
     */
    @JsonProperty("avatar_url")
    private String avatarUrl;

    /**
     * embedded rich content
     */
    private List<DiscordEmbed> embeds;

}
