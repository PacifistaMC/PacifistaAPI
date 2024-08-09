package fr.pacifista.api.server.essentials.service.holograms.services;

import com.funixproductions.core.crud.services.ApiService;
import com.funixproductions.core.exceptions.ApiBadRequestException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.essentials.client.holograms.dtos.HologramDTO;
import fr.pacifista.api.server.essentials.service.holograms.entities.Hologram;
import fr.pacifista.api.server.essentials.service.holograms.mappers.HologramMapper;
import fr.pacifista.api.server.essentials.service.holograms.repositories.HologramRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class HologramService extends ApiService<HologramDTO, Hologram, HologramMapper, HologramRepository> {

    public HologramService(HologramMapper mapper,
                           HologramRepository repository) {
        super(repository, mapper);
    }

    @Override
    public void beforeSavingEntity(@NonNull Iterable<Hologram> entity) {
        Hologram hologramParent;

        for (final Hologram hologram : entity) {
            if (hologram.getId() != null) {
                throw new ApiBadRequestException("Modification d'hologramme non autorisée");
            }

            hologramParent = hologram.getParentHologram();
            if (hologramParent != null && hologramParent.getUuid() != null) {
                hologramParent = findParentHologram(hologramParent.getUuid());

                if (hologramParent.getParentHologram() != null) {
                    throw new ApiBadRequestException("Vous ne pouvez pas créer d'holograme fils sur un parent qui est aussi un fils.");
                } else {
                    hologram.setParentHologram(hologramParent);
                }
            }
        }
    }

    @NonNull
    private Hologram findParentHologram(final UUID uuid) {
        final Optional<Hologram> search = getRepository().findByUuid(uuid.toString());

        if (search.isEmpty()) {
            throw new ApiNotFoundException("Hologramme parent non trouvé");
        } else {
            return search.get();
        }
    }
}
