package fr.pacifista.api.web.news.client.dtos.news;

import com.funixproductions.core.files.dtos.ApiStorageFileDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacifistaNewsImageDTO extends ApiStorageFileDTO {

    /**
     * Image rattachée à la news
     */
    @NotNull
    private UUID newsUuid;

    /**
     * Image en basse résolution pour l'affichage par liste
     */
    @NotNull
    private Boolean isLowResolution;

}
