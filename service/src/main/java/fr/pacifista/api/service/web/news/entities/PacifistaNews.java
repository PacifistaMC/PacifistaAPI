package fr.pacifista.api.service.web.news.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_news")
public class PacifistaNews extends ApiEntity {

    @Column(name = "original_writer", nullable = false)
    private String originalWriter;

    @Column(name = "update_writer")
    private String updateWriter;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "subtitle", nullable = false)
    private String subtitle;

    @Column(name = "article_image_url", nullable = false, length = 2000)
    private String articleImageUrl;

    @Column(name = "body", nullable = false, length = 20000)
    private String body;
}
