package fr.pacifista.api.client.web.news.dtos;

import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacifistaNewsDTO extends ApiDTO {

    private String originalWriter;

    private String updateWriter;

    private String name;

    private String title;

    private String subtitle;

    private String articleImageUrl;

    private String body;

    private Integer likesAmount;

    private Integer commentsAmount;

}
