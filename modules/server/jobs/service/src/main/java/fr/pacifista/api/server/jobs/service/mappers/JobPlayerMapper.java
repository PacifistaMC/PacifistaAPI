package fr.pacifista.api.server.jobs.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerDTO;
import fr.pacifista.api.server.jobs.service.entities.JobPlayer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobPlayerMapper extends ApiMapper<JobPlayer, JobPlayerDTO> {
}
