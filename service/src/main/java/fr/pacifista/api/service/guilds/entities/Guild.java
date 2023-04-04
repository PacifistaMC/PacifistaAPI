package fr.pacifista.api.service.guilds.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
