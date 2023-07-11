package fr.pacifista.api.server.guilds.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_guilds_experiences")
public class GuildExperience extends ApiEntity {

    @OneToOne
    @JoinColumn(nullable = false, name = "guild_id")
    private Guild guild;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer experience;

}
