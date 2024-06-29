package fr.pacifista.api.web.user.service.resources;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.enums.SearchOperation;
import com.funixproductions.core.crud.resources.ApiResource;
import com.funixproductions.core.exceptions.ApiUnauthorizedException;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebLegalUserDTO;
import fr.pacifista.api.web.user.client.enums.PacifistaLegalDocumentType;
import fr.pacifista.api.web.user.service.services.PacifistaWebLegalUserService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/web/user/legal-document-user")
public class PacifistaWebLegalUserResource extends ApiResource<PacifistaWebLegalUserDTO, PacifistaWebLegalUserService> {

    private final CurrentSession currentSession;

    public PacifistaWebLegalUserResource(PacifistaWebLegalUserService service,
                                         CurrentSession currentSession) {
        super(service);
        this.currentSession = currentSession;
    }

    @GetMapping("/self")
    public Collection<PacifistaWebLegalUserDTO> getUserLegalDocuments() {
        return fetchUserLegalDocuments(fetchCurrentUser());
    }

    @PostMapping("/self")
    public Collection<PacifistaWebLegalUserDTO> selfUpdate(@RequestBody List<PacifistaLegalDocumentType> legalDocumentsType) {
        if (legalDocumentsType == null || legalDocumentsType.isEmpty()) {
            throw new IllegalArgumentException("La liste des documents légaux ne doit pas être vide.");
        }

        final UserDTO userDTO = fetchCurrentUser();
        final List<PacifistaWebLegalUserDTO> userLegalDocuments = fetchUserLegalDocuments(userDTO);
        final List<PacifistaLegalDocumentType> documentTypes = new ArrayList<>(legalDocumentsType);
        final List<PacifistaWebLegalUserDTO> updatedUserLegalDocuments = new ArrayList<>();
        final List<PacifistaWebLegalUserDTO> createdUserLegalDocuments = new ArrayList<>();

        for (final PacifistaWebLegalUserDTO legal : userLegalDocuments) {
            for (final PacifistaLegalDocumentType legalDocumentType : legalDocumentsType) {
                if (legalDocumentType.equals(legal.getType())) {
                    legal.setUpdatedAt(new Date());
                    updatedUserLegalDocuments.add(legal);
                    documentTypes.remove(legalDocumentType);
                    break;
                }
            }
        }

        for (final PacifistaLegalDocumentType legalDocumentType : documentTypes) {
            createdUserLegalDocuments.add(new PacifistaWebLegalUserDTO(
                    userDTO.getId(),
                    legalDocumentType
            ));
        }
        getService().update(updatedUserLegalDocuments);
        getService().create(createdUserLegalDocuments);
        return fetchUserLegalDocuments(userDTO);
    }

    private List<PacifistaWebLegalUserDTO> fetchUserLegalDocuments(final @NonNull UserDTO userDTO) {
        return getService().getAll("0", "300", String.format(
                "userId:%s:%s",
                SearchOperation.EQUALS.getOperation(),
                userDTO.getId()
        ), "").getContent();
    }

    private UserDTO fetchCurrentUser() {
        final UserDTO userDTO = currentSession.getCurrentUser();

        if (userDTO == null) {
            throw new ApiUnauthorizedException("Vous n'êtes pas connecté");
        }

        return userDTO;
    }
}
