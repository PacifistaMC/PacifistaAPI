package fr.pacifista.api.server.jobs.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskDTO;

public class JobPlayerTaskClientImpl extends FeignImpl<JobPlayerTaskDTO, JobPlayerTaskClient> implements JobPlayerTaskClient {

    public static final String PATH = "jobs/player-tasks";

    public JobPlayerTaskClientImpl() {
        super(PATH, JobPlayerTaskClient.class);
    }
}
