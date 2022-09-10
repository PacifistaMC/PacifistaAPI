package fr.pacifista.api.service.boxes.entities;

import fr.funixgaming.api.core.crud.entities.ApiEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity(name = "pacifista_box_reward")
public class BoxReward extends ApiEntity {

    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private Box box;

    @Column(name = "item_serialized", nullable = false, columnDefinition = "LONGTEXT")
    private String itemSerialized;

    @Column(nullable = false)
    private Float rarity;

}
