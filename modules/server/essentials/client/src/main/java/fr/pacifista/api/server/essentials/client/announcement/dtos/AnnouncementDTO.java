package fr.pacifista.api.server.essentials.client.announcement.dtos;

import com.funixproductions.core.crud.dtos.ApiDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDTO extends ApiDTO {

    @NotBlank(message = "Le titre de message est requis.")
    @Size(min = 3, message = "Le titre de l'annonce doit au moins faire 3 caractères.")
    private String title;

    @NotBlank(message = "Vous devez spécifier le message de l'annonce.")
    @Size(min = 10, message = "Le message de l'annonce doit au moins faire 10 caractères.")
    private String message;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final AnnouncementDTO other) {
            return (message != null && message.equals(other.message)) &&
                    (title != null && title.equals(other.title)) &&
                    super.equals(other);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() +
                (message != null ? message.hashCode() : 0) +
                (title != null ? title.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "AnnouncementDTO{" +
                "message='" + message + "'" +
                ", title=" + title +
                ", id=" + super.getId() +
                '}';
    }
}
