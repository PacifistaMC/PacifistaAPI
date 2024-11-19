package fr.pacifista.api.web.news.service.services.comments;

import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentDTO;
import fr.pacifista.api.web.news.service.entities.comments.PacifistaNewsComment;
import fr.pacifista.api.web.news.service.mappers.comments.PacifistaNewsCommentMapper;
import fr.pacifista.api.web.news.service.repositories.comments.PacifistaNewsCommentRepository;
import fr.pacifista.api.web.news.service.services.PacifistaNewsUserService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacifistaNewsCommentCrudService extends PacifistaNewsUserService<PacifistaNewsCommentDTO, PacifistaNewsComment, PacifistaNewsCommentMapper, PacifistaNewsCommentRepository> {

    public PacifistaNewsCommentCrudService(PacifistaNewsCommentRepository repository,
                                           PacifistaNewsCommentMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PacifistaNewsComment> pacifistaNewsComments) {
        super.beforeSavingEntity(pacifistaNewsComments);

        PacifistaNewsComment parent;
        for (PacifistaNewsComment pacifistaNewsComment : pacifistaNewsComments) {
            parent = pacifistaNewsComment.getParent();

            if (parent != null && parent.getUuid() != null) {
                parent = this.getRepository().findByUuid(parent.getUuid().toString()).orElseThrow(() -> new ApiNotFoundException("Le commentaire parent n'existe pas"));
                pacifistaNewsComment.setParent(parent);
            }
        }
    }

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<PacifistaNewsComment> entities) {
        final List<PacifistaNewsComment> comments = new ArrayList<>();

        for (PacifistaNewsComment pacifistaNewsComment : entities) {
            comments.add(pacifistaNewsComment);
        }
        super.getRepository().deleteAllByParentIsIn(comments);
    }
}
