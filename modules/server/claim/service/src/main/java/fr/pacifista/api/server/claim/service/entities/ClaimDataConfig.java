package fr.pacifista.api.server.claim.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "claim_data_config")
public class ClaimDataConfig extends ApiEntity {

    @OneToOne
    @JoinColumn(name = "claim_id", nullable = false)
    private ClaimData claim;

    @Column(name = "explosion_enabled", nullable = false)
    private Boolean explosionEnabled;

    @Column(name = "fire_spread_enabled", nullable = false)
    private Boolean fireSpreadEnabled;

    @Column(name = "mob_griefing_enabled", nullable = false)
    private Boolean mobGriefingEnabled;

    @Column(name = "pvp_enabled", nullable = false)
    private Boolean pvpEnabled;

    @Column(name = "public_access", nullable = false)
    private Boolean publicAccess;

    @Column(name = "public_interact_buttons", nullable = false)
    private Boolean publicInteractButtons;

    @Column(name = "public_interact_doors_trap_doors", nullable = false)
    private Boolean publicInteractDoorsTrapDoors;

    @Column(name = "public_interact_chests", nullable = false)
    private Boolean publicInteractChests;

    @Column(name = "animal_protection", nullable = false)
    private Boolean animalProtection;

    @Column(name = "grief_protection", nullable = false)
    private Boolean griefProtection;

}
