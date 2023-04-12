package fr.pacifista.api.service.core.auth.services;

import fr.pacifista.api.service.core.auth.entities.Session;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ActualSession {

    @Nullable
    public Session getActualSession() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();

        if (authentication == null) {
            return null;
        } else {
            final Object principal = authentication.getPrincipal();

            if (principal instanceof final Session session) {
                return session;
            } else {
                return null;
            }
        }
    }

}
