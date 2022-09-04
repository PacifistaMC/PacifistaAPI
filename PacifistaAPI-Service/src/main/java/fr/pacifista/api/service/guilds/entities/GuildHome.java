package fr.pacifista.api.service.guilds.entities;

import fr.pacifista.api.service.core.entities.Location;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
