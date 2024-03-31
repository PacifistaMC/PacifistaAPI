package fr.pacifista.api.server.claim.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.claim.client.dtos.ClaimDataConfigDTO;
import fr.pacifista.api.server.claim.service.entities.ClaimDataConfig;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClaimDataConfigMapper extends ApiMapper<ClaimDataConfig, ClaimDataConfigDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "claim.uuid", source = "claimDataId")
    ClaimDataConfig toEntity(ClaimDataConfigDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "claimDataId", source = "claim.uuid")
    ClaimDataConfigDTO toDto(ClaimDataConfig entity);

    @Override
    @Mapping(target = "claim", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(ClaimDataConfig request, @MappingTarget ClaimDataConfig toPatch);
}
