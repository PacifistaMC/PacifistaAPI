package fr.pacifista.api.service.sanctions.mappers;

import fr.funixgaming.api.core.crud.mappers.ApiMapper;
import fr.pacifista.api.client.sanctions.dtos.SanctionDTO;
import fr.pacifista.api.service.sanctions.entities.Sanction;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SanctionMapper extends ApiMapper<Sanction, SanctionDTO> {

    @Override
    @Mapping(target = "uuid", source = "id")
    @Mapping(target = "id", ignore = true)
    Sanction toEntity(SanctionDTO dto);

    @Override
    @Mapping(target = "id", source = "uuid")
    SanctionDTO toDto(Sanction entity);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(Sanction request, @MappingTarget Sanction toPatch);

}
