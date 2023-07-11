package fr.pacifista.api.server.box.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import fr.pacifista.api.core.client.enums.enums.ServerGameMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "pacifista_box")
public class Box extends ApiEntity {

    /**
     * Name of the box
     */
    @Column(name = "box_name", unique = true, nullable = false)
    private String boxName;

    /**
     * Display name of the box ingame
     */
    @Column(name = "box_display_name", nullable = false)
    private String boxDisplayName;

    /**
     * Description of the box
     */
    @Column(name = "box_description", nullable = false, length = 500)
    private String boxDescription;

    /**
     * Numbers of items this box can drop
     */
    @Column(name = "drop_amount", nullable = false)
    private Integer dropAmount;

    /**
     * Game mode where the box is available
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "game_mode", nullable = false)
    private ServerGameMode gameMode;

    /**
     * List of box rewards
     */
    @OneToMany(mappedBy = "box", orphanRemoval = true)
    private List<BoxReward> rewards;

}
