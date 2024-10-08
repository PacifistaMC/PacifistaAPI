package fr.pacifista.api.server.warps.service.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_warps_config")
public class WarpConfig extends ApiEntity {

    @OneToOne(mappedBy = "config")
    @JoinColumn(name = "warp_id", nullable = false)
    private Warp warp;

    @Column(nullable = false, name = "is_visible_in_menu")
    private Boolean isVisibleInMenu;

    @Column(nullable = false, name = "public_access")
    private Boolean publicAccess;

    @Column(nullable = false, name = "is_free_to_use")
    private Boolean isFreeToUse;

    @Column(nullable = false, name = "price")
    private Double price;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final WarpConfig other) {
            return warp.getUuid().equals(other.warp.getUuid()) &&
                    isVisibleInMenu.equals(other.isVisibleInMenu) &&
                    publicAccess.equals(other.publicAccess) &&
                    isFreeToUse.equals(other.isFreeToUse) &&
                    price.equals(other.price) &&
                    super.equals(obj);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return warp.getUuid().hashCode() +
                isVisibleInMenu.hashCode() +
                publicAccess.hashCode() +
                isFreeToUse.hashCode() +
                price.hashCode() +
                super.hashCode();
    }

    public Warp getWarp() {
        this.warp.setConfig(null);
        return this.warp;
    }
}
