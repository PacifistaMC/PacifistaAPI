package fr.pacifista.api.web.news.client.clients;

import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.core.client.clients.FeignTokenInterceptor;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsLikeDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        name = "PacifistaNews",
        url = "${pacifista.web.news.api.app-domain-url}",
        path = "/web/news",
        configuration = FeignTokenInterceptor.class
)
public interface PacifistaNewsClient {

    /**
     * Récupère toutes les news, ajoute les news brouillon si l'utilisateur actuel est admin
     * @param page page
     * @return la liste des news
     */
    @GetMapping
    PageDTO<PacifistaNewsDTO> getAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page);

    /**
     * Récupère une news par son id
     * @param newsId newsId
     * @return la news
     */
    @GetMapping("{newsId}")
    PacifistaNewsDTO getNewsById(@PathVariable("newsId") String newsId);

    @GetMapping("/named/{newsName}")
    PacifistaNewsDTO getNewsByName(@PathVariable("newsName") String newsName);

    @GetMapping("/file/{imageId}")
    Resource getImageNews(@PathVariable("imageId") String imageId);

    /**
     * Créer une news, demande les droits Admin pour ça
     * @param newsDTO news
     * @param image image
     * @return la news crée
     */
    @PostMapping
    PacifistaNewsDTO createNews(@RequestPart("dto") @Valid PacifistaNewsDTO newsDTO, @RequestPart("image") MultipartFile image);

    /**
     * Met a jour une news, demande les droits Admin pour ça
     * @param newsDTO news
     * @return la news mise à jour
     */
    @PutMapping
    PacifistaNewsDTO updateNews(@RequestBody @Valid PacifistaNewsDTO newsDTO);

    /**
     * Met a jour une news et change l'image, demande les droits Admin pour ça
     * @param newsDTO news
     * @param image image
     * @return la news mise à jour
     */
    @PostMapping("/file")
    PacifistaNewsDTO updateNews(@RequestPart("dto") @Valid PacifistaNewsDTO newsDTO, @RequestPart("image") MultipartFile image);

    /**
     * Supprime une news, demande les droits Admin pour cela
     * @param newsId l'id de la news
     */
    @DeleteMapping
    void deleteNews(@RequestParam(value = "id") String newsId);

    /**
     * Récupère la liste des likes sur une news
     * @param newsId la news
     * @return la liste des likes
     */
    @GetMapping("/likes/{newsId}")
    PageDTO<PacifistaNewsLikeDTO> getLikesOnNews(
            @PathVariable("newsId") String newsId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page
    );

    /**
     * Ajoute un like à la news, demande à être connecté et a avoir un compte Minecraft lié
     * @param newsId id de la news
     * @return le like réalisé
     */
    @PostMapping("/like/{newsId}")
    PacifistaNewsLikeDTO likeNews(@PathVariable("newsId") String newsId);

    /**
     * Supprime un like à la news, demande à être connecté et a avoir un compte Minecraft lié. Si l'utilisateur est Modé
     * @param newsId id de la news
     */
    @DeleteMapping("/like/{newsId}")
    void removeLikeOnNews(@PathVariable("newsId") String newsId);

}
