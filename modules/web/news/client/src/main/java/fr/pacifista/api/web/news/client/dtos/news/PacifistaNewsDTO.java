package fr.pacifista.api.web.news.client.dtos.news;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PacifistaNewsDTO extends ApiDTO {

    /**
     * Nom d'utilisateur Minecraft du rédacteur d'origine
     */
    private String originalWriter;

    /**
     * Nom d'utilisateur Minecraft de la personne qui l'a modifié
     */
    private String updateWriter;

    /**
     * Nom de l'article présent dans l'url
     */
    @NotBlank(message = "Le nom unique de l'article est requis")
    private String name;

    /**
     * Nom de l'article sur la page
     */
    @NotBlank(message = "Le titre de l'article est requis")
    private String title;

    /**
     * Sous titre de l'article
     */
    @NotBlank(message = "Le sous-titre de l'article est requis")
    private String subtitle;

    /**
     * Image id pour l'image en taille réelle
     */
    private UUID articleImageId;

    /**
     * Image id pour l'image en taille résuite pour l'affichage par liste
     */
    private UUID articleImageIdLowRes;

    /**
     * Le contenu HTML de l'article
     */
    @NotBlank(message = "Il manque le corps HTML de l'article")
    private String bodyHtml;

    /**
     * Le contenu MarkDown de l'article pour la modification
     */
    @NotBlank(message = "Il manque le contenu en markdown de l'article")
    private String bodyMarkdown;

    /**
     * Si la news est publiée ou pas
     */
    @NotNull(message = "Vous devez spécifier sur la news est un brouillon ou non.")
    private Boolean draft;

    /**
     * Nombre de likes
     */
    private Integer likes;

    /**
     * Nombre de commentaires
     */
    private Integer comments;

    /**
     * Nombre de vues
     */
    private Integer views;

}