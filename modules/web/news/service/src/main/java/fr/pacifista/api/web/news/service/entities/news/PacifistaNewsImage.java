package fr.pacifista.api.web.news.service.entities.news;

import com.funixproductions.core.files.entities.ApiStorageFile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pacifista_news_images")
public class PacifistaNewsImage extends ApiStorageFile {

    @OneToOne
    @JoinColumn(name = "news_id", nullable = false)
    private PacifistaNews news;

    @Column(name = "is_low_resolution", nullable = false, updatable = false)
    private Boolean isLowResolution;

}
