package fr.pacifista.api.server.claim.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.claim.client.dtos.ClaimPhantomPreventionBlockDTO;
import fr.pacifista.api.server.claim.service.entities.ClaimPhantomPreventionBlock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClaimPhantomPreventionBlockMapper extends ApiMapper<ClaimPhantomPreventionBlock, ClaimPhantomPreventionBlockDTO> {
}
