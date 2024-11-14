package fr.pacifista.api.web.news.service.entities.news;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

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

    @Column(name = "article_image_uuid")
    private String articleImageId;

    @Column(name = "article_image_low_res_uuid")
    private String articleImageIdLowRes;

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

    public UUID getArticleImageId() {
        if (articleImageId == null) {
            return null;
        } else {
            return UUID.fromString(articleImageId);
        }
    }

    public UUID getArticleImageIdLowRes() {
        if (articleImageIdLowRes == null) {
            return null;
        } else {
            return UUID.fromString(articleImageIdLowRes);
        }
    }

    public void setArticleImageId(UUID articleImageId) {
        if (articleImageId == null) {
            this.articleImageId = null;
        } else {
            this.articleImageId = articleImageId.toString();
        }
    }

    public void setArticleImageIdLowRes(UUID articleImageIdLowRes) {
        if (articleImageIdLowRes == null) {
            this.articleImageIdLowRes = null;
        } else {
            this.articleImageIdLowRes = articleImageIdLowRes.toString();
        }
    }
}
