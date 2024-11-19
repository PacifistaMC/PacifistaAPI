package fr.pacifista.api.web.news.service.services.ban;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.web.news.client.dtos.ban.PacifistaNewsBanDTO;
import fr.pacifista.api.web.news.service.entities.ban.PacifistaNewsBan;
import fr.pacifista.api.web.news.service.mappers.ban.PacifistaNewsBanMapper;
import fr.pacifista.api.web.news.service.repositories.ban.PacifistaNewsBanRepository;
import fr.pacifista.api.web.user.client.components.PacifistaWebUserLinkComponent;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PacifistaNewsBanCrudService extends ApiService<PacifistaNewsBanDTO, PacifistaNewsBan, PacifistaNewsBanMapper, PacifistaNewsBanRepository> {

    private final CurrentSession actualSession;
    private final PacifistaWebUserLinkComponent userLinkComponent;

    public PacifistaNewsBanCrudService(PacifistaNewsBanRepository repository,
                                       PacifistaNewsBanMapper mapper,
                                       PacifistaWebUserLinkComponent userLinkComponent,
                                       CurrentSession actualSession) {
        super(repository, mapper);
        this.userLinkComponent = userLinkComponent;
        this.actualSession = actualSession;
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<PacifistaNewsBan> bans) {
        final UserDTO currentUser = this.actualSession.getCurrentUser();
        if (currentUser == null) {
            throw new ApiBadRequestException("Vous n'êtes pas connecté.");
        }
        final UUID currentUserFunixProdId = currentUser.getId();
        final PacifistaWebUserLinkDTO minecraftAccount = this.userLinkComponent.getLink(currentUser);
        final String currentUserMinecraftUsername = minecraftAccount.getMinecraftUsername();

        for (final PacifistaNewsBan ban : bans) {
            ban.setStaffFunixProdUserId(currentUserFunixProdId);
            ban.setStaffMinecraftUserName(currentUserMinecraftUsername);

            if (ban.getId() == null) {
                this.checkIfBanAlreadyExists(ban);
            }
        }
    }

    private void checkIfBanAlreadyExists(final PacifistaNewsBan ban) throws ApiBadRequestException {
        if (this.getRepository().existsByMinecraftUserNameBannedIgnoreCaseOrFunixProdUserIdBanned(
                ban.getMinecraftUserNameBanned(),
                ban.getFunixProdUserIdBanned().toString())
        ) {
            throw new ApiBadRequestException(String.format("Ce bannissement existe déjà pour le joueur %s.", ban.getMinecraftUserNameBanned()));
        }
    }

    public boolean isCurrentUserBanned() {
        final UserDTO user = this.actualSession.getCurrentUser();
        final PacifistaWebUserLinkDTO userLink = this.userLinkComponent.getLink(user);
        assert user != null;

        return this.getRepository().existsByMinecraftUserNameBannedIgnoreCaseOrFunixProdUserIdBanned(
                userLink.getMinecraftUsername(),
                user.getId().toString()
        );
    }
}
