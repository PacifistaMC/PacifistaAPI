package fr.pacifista.api.web.news.client.clients;

import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentDTO;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentLikeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "PacifistaNewsComments",
        url = "${pacifista.web.news.api.app-domain-url}",
        path = "/web/news/comments",
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaNewsCommentClient {

    @GetMapping
    PageDTO<PacifistaNewsCommentDTO> getCommentsOnNews(
            @RequestParam(value = "newsId") String newsId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page
    );

    @GetMapping("replies")
    PageDTO<PacifistaNewsCommentDTO> getCommentsRepliesOnNews(
            @RequestParam(value = "commentId") String commentId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page
    );

    @GetMapping("user")
    PageDTO<PacifistaNewsCommentDTO> getCommentsByUser(
            @RequestParam(value = "minecraftUsername") String minecraftUsername,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page
    );

    @PostMapping
    PacifistaNewsCommentDTO createComment(@RequestBody @Valid PacifistaNewsCommentDTO commentDTO);

    @PatchMapping("{commentId}")
    PacifistaNewsCommentDTO updateComment(
            @PathVariable("commentId") String commentId,
            @RequestBody
            @NotBlank(message = "Le commentaire ne doit pas être vide.")
            @Size(message = "Le commentaire doit contenir entre 2 et 1500 caractrères.", min = 2, max = 1500)
            String comment
    );

    @DeleteMapping("{commentId}")
    void deleteComment(@PathVariable("commentId") String commentId);

    @GetMapping("/likes")
    PageDTO<PacifistaNewsCommentLikeDTO> getLikesOnComment(
            @RequestParam(value = "commentId") String commentId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page
    );

    @PostMapping("/like/{commentId}")
    PacifistaNewsCommentLikeDTO likeComment(@PathVariable("commentId") String commentId);

    @DeleteMapping("/like/{commentId}")
    void removeLike(@PathVariable("commentId") String commentId);

}
