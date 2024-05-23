package fr.pacifista.api.web.user.service.resources;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkClientImpl;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import fr.pacifista.api.web.user.service.services.PacifistaWebUserLinkService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/" + PacifistaWebUserLinkClientImpl.PATH + "/public")
@RequiredArgsConstructor
public class PacifistaWebUserLinkResourcePublic {

    private final PacifistaWebUserLinkService service;
    private final CurrentSession currentSession;

    @PostMapping("/link")
    public PacifistaWebUserLinkDTO createLink(@RequestBody @NotBlank(message = "Le joueur minecraft ne peut pas être vide") String minecraftUsername) {
        return service.create(
                new PacifistaWebUserLinkDTO(getCurrentUser().getId(), service.getMinecraftUserId(minecraftUsername))
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



}
