package fr.pacifista.api.web.news.service.services.news;

import com.funixproductions.core.files.services.ApiStorageService;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsImageDTO;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNewsImage;
import fr.pacifista.api.web.news.service.mappers.news.PacifistaNewsImageMapper;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsImageRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacifistaNewsImageCrudService extends ApiStorageService<PacifistaNewsImageDTO, PacifistaNewsImage, PacifistaNewsImageMapper, PacifistaNewsImageRepository> {

    public PacifistaNewsImageCrudService(PacifistaNewsImageRepository repository,
                                         PacifistaNewsImageMapper mapper,
                                         Environment environment) {
        super("pacifista-news", environment.getProperty("file.storage.base-path"), repository, mapper);
    }

    @Transactional
    protected void deleteAllByNews(@NonNull PacifistaNews news) {
        final List<PacifistaNewsImage> images = this.getRepository().findAllByNewsUuid(news.getUuid().toString());
        final ArrayList<String> ids = new ArrayList<>();

        for (final PacifistaNewsImage image : images) {
            ids.add(image.getUuid().toString());
        }

        this.delete(ids.toArray(new String[0]));
    }
}
