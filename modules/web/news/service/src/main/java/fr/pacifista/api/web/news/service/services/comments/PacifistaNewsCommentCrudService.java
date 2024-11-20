package fr.pacifista.api.web.news.service.services.comments;

import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentDTO;
import fr.pacifista.api.web.news.service.entities.comments.PacifistaNewsComment;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import fr.pacifista.api.web.news.service.mappers.comments.PacifistaNewsCommentMapper;
import fr.pacifista.api.web.news.service.repositories.comments.PacifistaNewsCommentRepository;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsRepository;
import fr.pacifista.api.web.news.service.services.PacifistaNewsUserService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PacifistaNewsCommentCrudService extends PacifistaNewsUserService<PacifistaNewsCommentDTO, PacifistaNewsComment, PacifistaNewsCommentMapper, PacifistaNewsCommentRepository> {

    private final PacifistaNewsRepository newsRepository;

    public PacifistaNewsCommentCrudService(PacifistaNewsCommentRepository repository,
                                           PacifistaNewsCommentMapper mapper,
                                           PacifistaNewsRepository newsRepository) {
        super(repository, mapper);
        this.newsRepository = newsRepository;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PacifistaNewsComment> pacifistaNewsComments) {
        super.beforeSavingEntity(pacifistaNewsComments);

        PacifistaNewsComment parent;
        for (PacifistaNewsComment pacifistaNewsComment : pacifistaNewsComments) {
            parent = pacifistaNewsComment.getParent();

            if (parent != null && parent.getUuid() != null) {
                parent = this.getRepository().findByUuid(parent.getUuid().toString()).orElseThrow(() -> new ApiNotFoundException("Le commentaire parent n'existe pas"));
                if (parent.getParent() != null) {
                    throw new ApiBadRequestException("Les commentaires imbriqués ne sont pas autorisés (parent -> enfant -> petit-enfant)");
                }

                pacifistaNewsComment.setParent(parent);
            }
        }
    }

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<PacifistaNewsComment> entities) {
        final List<PacifistaNewsComment> comments = new ArrayList<>();
        final Map<PacifistaNews, Integer> removeLikesFromNews = new HashMap<>();

        PacifistaNews news;
        for (PacifistaNewsComment pacifistaNewsComment : entities) {
            news = pacifistaNewsComment.getNews();

            comments.add(pacifistaNewsComment);

            if (news != null) {
                removeLikesFromNews.put(news, removeLikesFromNews.getOrDefault(news, 0) + 1);
            }
        }
        super.getRepository().deleteAllByParentIsIn(comments);

        final List<PacifistaNews> newsToSave = new ArrayList<>();

        for (final Map.Entry<PacifistaNews, Integer> entry : removeLikesFromNews.entrySet()) {
            news = entry.getKey();
            news.setComments(news.getComments() - entry.getValue());
            if (news.getComments() < 0) {
                news.setComments(0);
            }

            newsToSave.add(news);
        }

        this.newsRepository.saveAll(newsToSave);
    }
}
