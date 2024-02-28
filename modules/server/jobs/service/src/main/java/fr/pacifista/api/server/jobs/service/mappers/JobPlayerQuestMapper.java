package fr.pacifista.api.server.jobs.service.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerQuestDTO;
import fr.pacifista.api.server.jobs.service.entities.JobPlayerQuest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobPlayerQuestMapper extends ApiMapper<JobPlayerQuest, JobPlayerQuestDTO> {
}
