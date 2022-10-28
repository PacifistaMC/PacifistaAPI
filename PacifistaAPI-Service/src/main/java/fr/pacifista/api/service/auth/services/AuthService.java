package fr.pacifista.api.service.auth.services;

import fr.funixgaming.api.client.user.dtos.UserDTO;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Nullable
    public UserDTO getActualUser() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();

        if (authentication == null) {
            return null;
        } else {
            final Object principal = authentication.getPrincipal();

            if (principal instanceof UserDTO) {
                return (UserDTO) principal;
            } else {
                return null;
            }
        }
    }

}
