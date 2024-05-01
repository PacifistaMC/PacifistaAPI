package fr.pacifista.api.web.user.service.resources;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkClientImpl;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import fr.pacifista.api.web.user.service.services.PacifistaWebUserLinkService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/" + PacifistaWebUserLinkClientImpl.PATH + "/public")
@RequiredArgsConstructor
public class PacifistaWebUserLinkResourcePublic {

    private final PacifistaWebUserLinkService service;
    private final CurrentSession currentSession;

    @PostMapping("/public/link")
    public PacifistaWebUserLinkDTO createLink(@RequestBody @NotNull(message = "Le joueur minecraft ne peut pas être null") UUID minecraftUuid) {
        return service.create(
                new PacifistaWebUserLinkDTO(getCurrentUser().getId(), minecraftUuid)
        );
    }

    @PostMapping("/public/validate")
    public PacifistaWebUserLinkDTO validateLink(@RequestBody @NotBlank(message = "La clé de validation ne peut pas être null") String linkKey) {
        return service.validateLink(getCurrentUser().getId(), linkKey);
    }

    @PostMapping("/public/unlink")
    public void unlink() {
        service.unlink(getCurrentUser().getId());
    }

    @GetMapping("/public/linked")
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

}
