package fr.pacifista.api.server.claim.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataUserDTO;
import fr.pacifista.api.server.claim.service.entities.ClaimDataUser;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClaimDataUserMapper extends ApiMapper<ClaimDataUser, ClaimDataUserDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "claim.uuid", source = "claimDataId")
    ClaimDataUser toEntity(ClaimDataUserDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "claimDataId", source = "claim.uuid")
    ClaimDataUserDTO toDto(ClaimDataUser entity);

    @Override
    @Mapping(target = "claim", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(ClaimDataUser request, @MappingTarget ClaimDataUser toPatch);
}
