package fr.pacifista.api.server.jobs.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerBlockPlaceDTO;
import fr.pacifista.api.server.jobs.service.entities.JobPlayerBlockPlace;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobPlayerBlockPlaceMapper extends ApiMapper<JobPlayerBlockPlace, JobPlayerBlockPlaceDTO> {
}
