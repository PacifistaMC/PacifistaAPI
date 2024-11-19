package fr.pacifista.api.web.news.service.resources;

import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.web.news.client.clients.PacifistaNewsCommentClient;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentDTO;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentLikeDTO;
import fr.pacifista.api.web.news.service.services.comments.PacifistaNewsCommentCrudService;
import fr.pacifista.api.web.news.service.services.comments.PacifistaNewsCommentLikeCrudService;
import fr.pacifista.api.web.news.service.services.news.PacifistaNewsCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/news/comments")
@RequiredArgsConstructor
public class PacifistaNewsCommentResource implements PacifistaNewsCommentClient {

    private final PacifistaNewsCommentCrudService service;
    private final PacifistaNewsCommentLikeCrudService likeService;
    private final PacifistaNewsCrudService newsService;

    @Override
    public PageDTO<PacifistaNewsCommentDTO> getCommentsOnNews(String newsId, int page) {

    }

    @Override
    public PageDTO<PacifistaNewsCommentDTO> getCommentsRepliesOnNews(String commentId, int page) {

    }

    @Override
    public PageDTO<PacifistaNewsCommentDTO> getCommentsByUser(String minecraftUsername, int page) {

    }

    @Override
    public PacifistaNewsCommentDTO createComment(PacifistaNewsCommentDTO commentDTO) {

    }

    @Override
    public PacifistaNewsCommentDTO updateComment(String commentId, String comment) {

    }

    @Override
    public void deleteComment(String commentId) {

    }

    @Override
    public PageDTO<PacifistaNewsCommentLikeDTO> getLikesOnComment(String commentId, int page) {

    }

    @Override
    public PacifistaNewsCommentLikeDTO likeComment(String commentId) {

    }

    @Override
    public void removeLike(String commentId) {

    }
}
