package fr.pacifista.api.server.jobs.service.mappers;


import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskWorkerDTO;
import fr.pacifista.api.server.jobs.service.entities.JobPlayerTaskWorker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {JobPlayerTaskMapper.class})
public interface JobPlayerTaskWorkerMapper extends ApiMapper<JobPlayerTaskWorker, JobPlayerTaskWorkerDTO>{
}
