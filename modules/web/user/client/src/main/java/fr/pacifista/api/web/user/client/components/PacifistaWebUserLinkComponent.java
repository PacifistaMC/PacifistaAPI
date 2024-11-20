package fr.pacifista.api.web.user.client.components;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkInternalClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PacifistaWebUserLinkComponent {

    private final PacifistaWebUserLinkInternalClient internalClient;

    @NonNull
    public PacifistaWebUserLinkDTO getLink(final @Nullable UserDTO userDTO) throws ApiException {
        if (userDTO == null || userDTO.getId() == null) {
            throw new ApiBadRequestException("L'utilisateur actuel n'est pas connecté.");
        }

        final List<PacifistaWebUserLinkDTO> linkDto = internalClient.getAll(
                "0",
                "1",
                String.format(
                        "funixProdUserId:%s:%s",
                        SearchOperation.EQUALS.getOperation(),
                        userDTO.getId()
                ),
                ""
        ).getContent();

        try {
            final PacifistaWebUserLinkDTO minecraftAccount = linkDto.getFirst();

            if (Boolean.TRUE.equals(minecraftAccount.getLinked())) {
                return minecraftAccount;
            } else {
                throw new ApiBadRequestException(String.format("Vous n'avez pas confirmé la liaison de votre compte Minecraft %s.", minecraftAccount.getMinecraftUsername()));
            }
        } catch (NoSuchElementException e) {
            throw new ApiBadRequestException("Vous n'avez pas lié votre compte Minecraft.");
        }
    }

    @NonNull
    public PacifistaWebUserLinkDTO getLink(final @Nullable UUID userId) throws ApiException {
        final UserDTO userDTO = new UserDTO();

        userDTO.setId(userId);
        return this.getLink(userDTO);
    }

}
