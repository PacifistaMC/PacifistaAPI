package fr.pacifista.api.server.jobs.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerTaskWorkerDTO;

public class JobPlayerTaskWorkerClientImpl extends FeignImpl<JobPlayerTaskWorkerDTO, JobPlayerTaskWorkerClient> implements JobPlayerTaskWorkerClient {

    public static final String PATH = "jobs/player-tasks-workers";

    public JobPlayerTaskWorkerClientImpl() {
        super(PATH, JobPlayerTaskWorkerClient.class);
    }
}
