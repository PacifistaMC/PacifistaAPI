package fr.pacifista.api.web.news.service.services.news;

import com.funixproductions.core.files.services.ApiStorageService;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsImageDTO;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNewsImage;
import fr.pacifista.api.web.news.service.mappers.news.PacifistaNewsImageMapper;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsImageRepository;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacifistaNewsImageCrudService extends ApiStorageService<PacifistaNewsImageDTO, PacifistaNewsImage, PacifistaNewsImageMapper, PacifistaNewsImageRepository> {

    private final PacifistaNewsRepository newsRepository;

    public PacifistaNewsImageCrudService(PacifistaNewsImageRepository repository,
                                         PacifistaNewsImageMapper mapper,
                                         PacifistaNewsRepository newsRepository) {
        super("pacifista-news", repository, mapper);
        this.newsRepository = newsRepository;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PacifistaNewsImage> entities) {
        Optional<PacifistaNews> pacifistaNews;

        for (final PacifistaNewsImage image : entities) {
            pacifistaNews = this.newsRepository.findByUuid(image.getNews().getUuid().toString());
            pacifistaNews.ifPresent(image::setNews);
        }
    }

    @Transactional
    protected void deleteAllByNews(@NonNull PacifistaNews news) {
        final List<PacifistaNewsImage> images = this.getRepository().findAllByNews(news);
        final ArrayList<String> ids = new ArrayList<>();

        for (final PacifistaNewsImage image : images) {
            ids.add(image.getUuid().toString());
        }

        this.delete(ids.toArray(new String[0]));
    }
}
