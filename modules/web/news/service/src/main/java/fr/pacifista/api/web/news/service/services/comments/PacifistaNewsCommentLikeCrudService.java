package fr.pacifista.api.web.news.service.services.comments;

import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentDTO;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentLikeDTO;
import fr.pacifista.api.web.news.service.entities.comments.PacifistaNewsComment;
import fr.pacifista.api.web.news.service.entities.comments.PacifistaNewsCommentLike;
import fr.pacifista.api.web.news.service.mappers.comments.PacifistaNewsCommentLikeMapper;
import fr.pacifista.api.web.news.service.repositories.comments.PacifistaNewsCommentLikeRepository;
import fr.pacifista.api.web.news.service.repositories.comments.PacifistaNewsCommentRepository;
import fr.pacifista.api.web.news.service.services.PacifistaNewsUserService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PacifistaNewsCommentLikeCrudService extends PacifistaNewsUserService<PacifistaNewsCommentLikeDTO, PacifistaNewsCommentLike, PacifistaNewsCommentLikeMapper, PacifistaNewsCommentLikeRepository> {

    private final PacifistaNewsCommentRepository commentRepository;

    public PacifistaNewsCommentLikeCrudService(PacifistaNewsCommentLikeRepository repository,
                                               PacifistaNewsCommentLikeMapper mapper,
                                               PacifistaNewsCommentRepository commentRepository) {
        super(repository, mapper);
        this.commentRepository = commentRepository;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PacifistaNewsCommentLike> pacifistaNewsCommentLikes) {
        super.beforeSavingEntity(pacifistaNewsCommentLikes);

        PacifistaNewsComment comment;
        for (PacifistaNewsCommentLike pacifistaNewsCommentLike : pacifistaNewsCommentLikes) {
            comment = pacifistaNewsCommentLike.getComment();

            if (comment != null && comment.getUuid() != null) {
                comment = this.commentRepository.findByUuid(comment.getUuid().toString()).orElseThrow(() -> new ApiNotFoundException("Le commentaire n'existe pas"));
                pacifistaNewsCommentLike.setComment(comment);
            }
        }
    }

    public void deleteByComment(final PacifistaNewsCommentDTO comment) {
        this.getRepository().deleteAllByComment(
                this.commentRepository.findByUuid(comment.getId().toString()).orElseThrow(() -> new ApiNotFoundException("Le commentaire n'existe pas"))
        );
    }

}
