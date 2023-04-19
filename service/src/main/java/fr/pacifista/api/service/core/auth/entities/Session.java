package fr.pacifista.api.service.core.auth.entities;

import fr.funixgaming.api.client.user.dtos.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Session {

    private final UserDTO user;
    private final String clientIp;
    private final HttpServletRequest request;

}
