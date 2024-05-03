package fr.pacifista.api.web.user.service.resources;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.players.data.client.clients.PacifistaPlayerDataInternalClient;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerDataDTO;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkClientImpl;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import fr.pacifista.api.web.user.service.services.PacifistaWebUserLinkService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/" + PacifistaWebUserLinkClientImpl.PATH + "/public")
@RequiredArgsConstructor
public class PacifistaWebUserLinkResourcePublic {

    private final PacifistaWebUserLinkService service;
    private final CurrentSession currentSession;
    private final PacifistaPlayerDataInternalClient pacifistaPlayerDataInternalClient;

    @PostMapping("/link")
    public PacifistaWebUserLinkDTO createLink(@RequestBody @NotBlank(message = "Le joueur minecraft ne peut pas être vide") String minecraftUsername) {
        return service.create(
                new PacifistaWebUserLinkDTO(getCurrentUser().getId(), getMinecraftUserId(minecraftUsername))
        );
    }

    @PostMapping("/unlink")
    public void unlink() {
        service.unlink(getCurrentUser().getId());
    }

    @GetMapping("/linked")
    public PacifistaWebUserLinkDTO getLinked() {
        return service.findLinkedFromFunixProdUser(getCurrentUser().getId());
    }

    private UserDTO getCurrentUser() {
        final UserDTO user = this.currentSession.getCurrentUser();
        if (user == null) {
            throw new ApiBadRequestException("Vous devez être connecté pour effectuer cette action");
        } else {
            return user;
        }
    }

    private UUID getMinecraftUserId(final String username) throws ApiException {
        try {
            final List<PacifistaPlayerDataDTO> data = this.pacifistaPlayerDataInternalClient.getAll(
                    "0",
                    "1",
                    String.format(
                            "minecraftUsername:%s:%s",
                            SearchOperation.EQUALS.getOperation(),
                            username.toLowerCase()
                    ),
                    ""
            ).getContent();

            if (data.isEmpty()) {
                throw new ApiNotFoundException(String.format("Le joueur minecraft %s se s'est jamais connecté sur Pacifista.", username));
            } else {
                return data.get(0).getMinecraftUuid();
            }
        } catch (Exception e) {
            throw new ApiException("Erreur interne, impossible de récupérer l'identifiant du joueur minecraft.", e);
        }
    }

}
