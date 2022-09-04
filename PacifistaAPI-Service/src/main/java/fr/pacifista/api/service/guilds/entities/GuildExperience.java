package fr.pacifista.api.service.guilds.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
