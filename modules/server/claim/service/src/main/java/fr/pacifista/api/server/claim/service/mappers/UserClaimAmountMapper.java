package fr.pacifista.api.server.claim.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.claim.client.dtos.UserClaimAmountDTO;
import fr.pacifista.api.server.claim.service.entities.UserClaimAmount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserClaimAmountMapper extends ApiMapper<UserClaimAmount, UserClaimAmountDTO> {
}
