package fr.pacifista.api.server.claim.service.services;

import com.funixproductions.core.crud.services.ApiService;
import fr.pacifista.api.server.claim.client.dtos.UserClaimAmountDTO;
import fr.pacifista.api.server.claim.service.entities.UserClaimAmount;
import fr.pacifista.api.server.claim.service.mappers.UserClaimAmountMapper;
import fr.pacifista.api.server.claim.service.repositories.UserClaimAmountRepository;
import org.springframework.stereotype.Service;

@Service
public class UserClaimAmountService extends ApiService<UserClaimAmountDTO, UserClaimAmount, UserClaimAmountMapper, UserClaimAmountRepository> {

    public UserClaimAmountService(UserClaimAmountRepository repository, UserClaimAmountMapper mapper) {
        super(repository, mapper);
    }

}
