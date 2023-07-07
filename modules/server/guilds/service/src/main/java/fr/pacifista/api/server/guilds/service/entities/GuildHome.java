package fr.pacifista.api.server.guilds.service.entities;

import fr.pacifista.api.core.service.entities.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_guilds_homes")
public class GuildHome extends Location {
    @ManyToOne
    @JoinColumn(nullable = false, name = "guild_id")
    private Guild guild;

    @Column(nullable = false, name = "public_access")
    private Boolean publicAccess;
}
