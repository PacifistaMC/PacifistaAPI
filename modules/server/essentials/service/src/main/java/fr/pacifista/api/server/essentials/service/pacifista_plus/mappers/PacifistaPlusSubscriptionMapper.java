package fr.pacifista.api.server.essentials.service.pacifista_plus.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.essentials.client.pacifista_plus.dtos.PacifistaPlusSubscriptionDTO;
import fr.pacifista.api.server.essentials.service.pacifista_plus.entities.PacifistaPlusSubscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PacifistaPlusSubscriptionMapper extends ApiMapper<PacifistaPlusSubscription, PacifistaPlusSubscriptionDTO> {
}
