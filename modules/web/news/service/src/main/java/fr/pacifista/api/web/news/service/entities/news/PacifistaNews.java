package fr.pacifista.api.web.news.service.entities.news;

import com.funixproductions.core.crud.entities.ApiEntity;
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

    @Column(name = "body_html", nullable = false, length = 200000)
    private String bodyHtml;

    @Column(name = "body_markdown", nullable = false, length = 200000)
    private String bodyMarkdown;

    @Column(name = "draft", nullable = false)
    private Boolean draft;

    @Column(name = "likes", nullable = false)
    private Integer likes;

    @Column(name = "comments", nullable = false)
    private Integer comments;

    @Column(name = "views", nullable = false)
    private Integer views;
}
