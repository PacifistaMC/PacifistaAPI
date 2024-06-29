package fr.pacifista.api.server.essentials.service.announcement.entities;

import com.funixproductions.core.crud.entities.ApiEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "pacifista_announcements")
public class Announcement extends ApiEntity {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 2000)
    private String message;

}
