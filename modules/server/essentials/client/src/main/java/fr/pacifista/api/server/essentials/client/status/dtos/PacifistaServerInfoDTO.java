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

    private Integer onlinePlayers;

    private Integer playerSlots;

    private List<Server> servers;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Server {
        private String name;

        private String proxyName;

        private Boolean isOnline;

        private Integer onlinePlayers;

        private Integer playerSlots;

        private List<HttpPlayerConnected> players;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof final Server server) {
                return this.name.equals(server.getName()) && this.proxyName.equals(server.getProxyName());
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return super.hashCode() + this.name.hashCode() + this.proxyName.hashCode();
        }

        @Override
        public String toString() {
            return String.format(
                    "Server{name=%s, proxyName=%s, isOnline=%b, onlinePlayers=%d, playerSlots=%d, players=%s}",
                    this.name,
                    this.proxyName,
                    this.isOnline,
                    this.onlinePlayers,
                    this.playerSlots,
                    this.players
            );
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HttpPlayerConnected {

        private String name;

        private UUID uuid;

        private Integer latency;

        private Boolean afk;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof final HttpPlayerConnected httpPlayerConnected) {
                return this.uuid.equals(httpPlayerConnected.getUuid());
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return super.hashCode() + this.uuid.hashCode();
        }

        @Override
        public String toString() {
            return String.format(
                    "HttpPlayerConnected{name=%s, uuid=%s, latency=%d, afk=%b}",
                    this.name,
                    this.uuid,
                    this.latency,
                    this.afk
            );
        }
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
