package fr.pacifista.api.server.jobs.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskDTO;
import fr.pacifista.api.server.jobs.service.entities.JobPlayerTask;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobPlayerTaskMapper extends ApiMapper<JobPlayerTask, JobPlayerTaskDTO> {
}
