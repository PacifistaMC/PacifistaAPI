package fr.pacifista.api.service.guilds.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pacifista_guilds")
public class Guild extends ApiEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "guild", orphanRemoval = true)
    private List<GuildHome> homes;

    @OneToMany(mappedBy = "guild", orphanRemoval = true)
    private List<GuildMember> members;

    @OneToOne(mappedBy = "guild", orphanRemoval = true)
    private GuildExperience experience;

    @Column(nullable = false)
    private Double money;

}
