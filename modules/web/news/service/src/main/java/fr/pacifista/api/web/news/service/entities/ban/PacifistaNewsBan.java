package fr.pacifista.api.web.news.service.entities.ban;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pacifista_news_bans")
public class PacifistaNewsBan extends ApiEntity {

    @Column(name = "reason")
    private String reason;

    @Column(name = "minecraft_user_name_banned", nullable = false)
    private String minecraftUserNameBanned;

    @Column(name = "funixprod_user_id_banned", nullable = false)
    private String funixProdUserIdBanned;

    @Column(name = "staff_minecraft_user_name", nullable = false)
    private String staffMinecraftUserName;

    @Column(name = "staff_funixprod_user_id", nullable = false)
    private String staffFunixProdUserId;
}
