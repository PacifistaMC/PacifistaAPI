package fr.pacifista.api.web.news.service.services.news;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.mappers.ApiMapper;
import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.web.news.client.dtos.PacifistaNewsUserDataDTO;
import fr.pacifista.api.web.news.service.entities.PacifistaNewsUserData;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import fr.pacifista.api.web.news.service.repositories.PacifistaNewsUserDataRepository;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsRepository;
import fr.pacifista.api.web.user.client.components.PacifistaWebUserLinkComponent;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

abstract class PacifistaNewsUserService<DTO extends PacifistaNewsUserDataDTO, ENTITY extends PacifistaNewsUserData, MAPPER extends ApiMapper<ENTITY, DTO>, REPOSITORY extends PacifistaNewsUserDataRepository<ENTITY>> extends ApiService<DTO, ENTITY, MAPPER, REPOSITORY> {

    @Autowired
    private PacifistaNewsRepository newsRepository;

    @Autowired
    private PacifistaWebUserLinkComponent webUserLinkComponent;

    @Autowired
    private CurrentSession currentSession;

    protected PacifistaNewsUserService(REPOSITORY repository,
                                       MAPPER mapper) {
        super(repository, mapper);
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<ENTITY> entities) {
        final UserDTO userDTO = this.currentSession.getCurrentUser();
        if (userDTO == null) {
            throw new ApiBadRequestException("Vous n'êtes pas connecté");
        }
        final PacifistaWebUserLinkDTO minecraftAccount = this.webUserLinkComponent.getLink(userDTO);
        Optional<PacifistaNews> news;

        for (ENTITY entity : entities) {
            news = this.newsRepository.findByUuid(entity.getNews().getUuid().toString());
            news.ifPresent(entity::setNews);

            entity.setMinecraftUsername(minecraftAccount.getMinecraftUsername());
            entity.setFunixProdUserId(userDTO.getId());
        }
    }
}
