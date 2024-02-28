package fr.pacifista.api.server.jobs.service.repositories;

import com.funixproductions.core.crud.repositories.ApiRepository;
import fr.pacifista.api.server.jobs.service.entities.JobPlayer;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPlayerRepository extends ApiRepository<JobPlayer> {
}
