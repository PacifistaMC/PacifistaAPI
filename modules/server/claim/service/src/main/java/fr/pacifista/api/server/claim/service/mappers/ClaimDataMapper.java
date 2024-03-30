package fr.pacifista.api.server.claim.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataDTO;
import fr.pacifista.api.server.claim.service.entities.ClaimData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        ClaimDataConfigMapper.class,
        ClaimDataUserMapper.class
})
public interface ClaimDataMapper extends ApiMapper<ClaimData, ClaimDataDTO> {
}
