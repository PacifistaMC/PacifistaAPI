package fr.pacifista.api.server.jobs.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerDTO;

public class JobPlayerClientImpl extends FeignImpl<JobPlayerDTO, JobPlayerClient> implements JobPlayerClient {

    public static final String PATH = "jobs/player";

    public JobPlayerClientImpl() {
        super(PATH, JobPlayerClient.class);
    }
}
