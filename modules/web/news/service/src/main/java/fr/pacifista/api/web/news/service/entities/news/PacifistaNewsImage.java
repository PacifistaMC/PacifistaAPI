package fr.pacifista.api.web.news.service.entities.news;

import com.funixproductions.core.files.entities.ApiStorageFile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pacifista_news_images")
public class PacifistaNewsImage extends ApiStorageFile {

    @Column(name = "news_uuid", nullable = false)
    private String newsUuid;

    @Column(name = "is_low_resolution", nullable = false, updatable = false)
    private Boolean isLowResolution;

    public UUID getNewsUuid() {
        if (newsUuid == null) {
            return null;
        } else {
            return UUID.fromString(newsUuid);
        }
    }

    public void setNewsUuid(UUID newsUuid) {
        if (newsUuid == null) {
            this.newsUuid = null;
        } else {
            this.newsUuid = newsUuid.toString();
        }
    }
}
