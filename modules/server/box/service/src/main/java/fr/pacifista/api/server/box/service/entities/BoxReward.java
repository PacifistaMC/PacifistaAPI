package fr.pacifista.api.server.box.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_box_reward")
public class BoxReward extends ApiEntity {

    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private Box box;

    @Column(name = "item_serialized", nullable = false, length = 50000)
    private String itemSerialized;

    @Column(nullable = false)
    private Float rarity;

}
