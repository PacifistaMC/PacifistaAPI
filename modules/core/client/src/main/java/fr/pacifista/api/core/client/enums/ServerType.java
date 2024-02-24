package fr.pacifista.api.core.client.enums;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServerType {
    HUB("Hub", "hub", ServerGameMode.NEUTRAL),
    CREATIVE("Créatif", "creatif", ServerGameMode.CREATIVE),
    EVENT("Event", "server_event", ServerGameMode.NEUTRAL),
    SURVIE_ALPHA("SurvieAlpha", "survie_alpha", ServerGameMode.SURVIVAL),
    SURVIE_BETA("SurvieBeta", "survie_beta", ServerGameMode.SURVIVAL),
    SURVIE_RESOURCE("Ressource", "survie_ressource", ServerGameMode.SURVIVAL);

    private final String displayName;
    private final String proxyServerName;
    private final ServerGameMode gameMode;

    @Nullable
    public static ServerType getServerByDisplayName(final String name) {
        for (ServerType srv : ServerType.values()) {
            if (srv.getDisplayName().equalsIgnoreCase(name)) {
                return srv;
            }
        }
        return null;
    }

    @Nullable
    public static ServerType getServerByProxyName(final String name) {
        for (ServerType srv : ServerType.values()) {
            if (srv.getProxyServerName().equalsIgnoreCase(name)) {
                return srv;
            }
        }
        return null;
    }
}
