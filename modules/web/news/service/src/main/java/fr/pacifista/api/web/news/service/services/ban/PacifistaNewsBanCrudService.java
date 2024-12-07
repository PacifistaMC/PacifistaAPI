package fr.pacifista.api.web.news.service.services.ban;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.core.service.tools.discord.dtos.DiscordSendMessageWebHookDTO;
import fr.pacifista.api.core.service.tools.discord.services.DiscordMessagesService;
import fr.pacifista.api.web.news.client.dtos.ban.PacifistaNewsBanDTO;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentDTO;
import fr.pacifista.api.web.news.service.entities.ban.PacifistaNewsBan;
import fr.pacifista.api.web.news.service.mappers.ban.PacifistaNewsBanMapper;
import fr.pacifista.api.web.news.service.repositories.ban.PacifistaNewsBanRepository;
import fr.pacifista.api.web.news.service.services.comments.PacifistaNewsCommentCrudService;
import fr.pacifista.api.web.user.client.components.PacifistaWebUserLinkComponent;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class PacifistaNewsBanCrudService extends ApiService<PacifistaNewsBanDTO, PacifistaNewsBan, PacifistaNewsBanMapper, PacifistaNewsBanRepository> {

    private final CurrentSession actualSession;
    private final PacifistaWebUserLinkComponent userLinkComponent;
    private final PacifistaNewsCommentCrudService commentCrudService;

    private final DiscordMessagesService discordMessagesService;

    public PacifistaNewsBanCrudService(PacifistaNewsBanRepository repository,
                                       PacifistaNewsBanMapper mapper,
                                       PacifistaWebUserLinkComponent userLinkComponent,
                                       CurrentSession actualSession,
                                       PacifistaNewsCommentCrudService commentCrudService,
                                       DiscordMessagesService discordMessagesService) {
        super(repository, mapper);
        this.userLinkComponent = userLinkComponent;
        this.actualSession = actualSession;
        this.commentCrudService = commentCrudService;
        this.discordMessagesService = discordMessagesService;
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

    @Override
    public void afterSavingEntity(@NonNull Iterable<PacifistaNewsBan> entity) {
        final Set<String> minecraftUserNames = new HashSet<>();
        final Set<String> funixProdUserIds = new HashSet<>();

        for (final PacifistaNewsBan ban : entity) {
            minecraftUserNames.add(ban.getMinecraftUserNameBanned());
            funixProdUserIds.add(ban.getFunixProdUserIdBanned().toString());
        }

        final String query = String.format(
                "minecraftUsername:%s:[%s],funixProdUserId:%s:[%s]",
                SearchOperation.EQUALS_IGNORE_CASE.getOperation(),
                String.join("|", minecraftUserNames),
                SearchOperation.EQUALS_IGNORE_CASE.getOperation(),
                String.join("|", funixProdUserIds)
        );

        PageDTO<PacifistaNewsCommentDTO> comments = new PageDTO<>(new ArrayList<>(), 2, 0, 10L, 1);
        final ArrayList<String> commentsIds = new ArrayList<>();

        while (comments.getActualPage() < comments.getTotalPages()) {
            comments = this.commentCrudService.getAll(Integer.toString(comments.getActualPage()), "300", query, "");

            for (final PacifistaNewsCommentDTO comment : comments.getContent()) {
                commentsIds.add(comment.getId().toString());
            }
            comments.setActualPage(comments.getActualPage() + 1);
        }

        if (!commentsIds.isEmpty()) {
            this.commentCrudService.delete(commentsIds.toArray(new String[0]));
        }

        for (final PacifistaNewsBan ban : entity) {
            this.alertDiscordForNewBan(ban);
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

    @Override
    public void beforeDeletingEntity(@NonNull Iterable<PacifistaNewsBan> entity) {
        for (final PacifistaNewsBan ban : entity) {
            this.alertDiscordOnBanRemoved(ban);
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

    private void alertDiscordForNewBan(final PacifistaNewsBan ban) {
        final DiscordSendMessageWebHookDTO message = new DiscordSendMessageWebHookDTO();

        message.setContent(String.format(
                ":warning: L'utilisateur %s a été banni de l'espace commentaire pour la raison suivante : %s.\nPar %s",
                ban.getMinecraftUserNameBanned(),
                ban.getReason(),
                ban.getStaffMinecraftUserName()
        ));
        message.setUsername("Pacifista News");

        this.discordMessagesService.sendAlertMessage(message);
    }

    private void alertDiscordOnBanRemoved(final PacifistaNewsBan ban) {
        final DiscordSendMessageWebHookDTO message = new DiscordSendMessageWebHookDTO();

        message.setContent(String.format(
                ":warning: L'utilisateur %s a été unban de l'espace commentaire. Raison initiale : %s.\nBanni par %s",
                ban.getMinecraftUserNameBanned(),
                ban.getReason(),
                ban.getStaffMinecraftUserName()
        ));
        message.setUsername("Pacifista News");

        this.discordMessagesService.sendAlertMessage(message);
    }
}
