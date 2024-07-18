package fr.pacifista.api.server.essentials.client.status.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacifistaServerInfoDTO {

    private int onlinePlayers;

    private int playerSlots;

    private List<Server> servers;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Server {
        private String name;

        private String proxyName;

        private boolean isOnline;

        private int onlinePlayers;

        private int playerSlots;

        private List<HttpPlayerConnected> players;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HttpPlayerConnected {
        private String name;

        private UUID uuid;
    }

    @Override
    public String toString() {
        return String.format(
                "PacifistaServerInfo{onlinePlayers=%d, playerSlots=%d, servers=%s}",
                this.onlinePlayers,
                this.playerSlots,
                this.servers
        );
    }
}
