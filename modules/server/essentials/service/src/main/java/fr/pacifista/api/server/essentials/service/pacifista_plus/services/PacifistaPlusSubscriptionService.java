package fr.pacifista.api.server.essentials.service.pacifista_plus.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.essentials.client.pacifista_plus.dtos.PacifistaPlusSubscriptionDTO;
import fr.pacifista.api.server.essentials.service.pacifista_plus.entities.PacifistaPlusSubscription;
import fr.pacifista.api.server.essentials.service.pacifista_plus.mappers.PacifistaPlusSubscriptionMapper;
import fr.pacifista.api.server.essentials.service.pacifista_plus.repositories.PacifistaPlusSubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class PacifistaPlusSubscriptionService extends ApiService<PacifistaPlusSubscriptionDTO, PacifistaPlusSubscription, PacifistaPlusSubscriptionMapper, PacifistaPlusSubscriptionRepository> {

    public PacifistaPlusSubscriptionService(PacifistaPlusSubscriptionRepository repository, PacifistaPlusSubscriptionMapper mapper) {
        super(repository, mapper);
    }

}
