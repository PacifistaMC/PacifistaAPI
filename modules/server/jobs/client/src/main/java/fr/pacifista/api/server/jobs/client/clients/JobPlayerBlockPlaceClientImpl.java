package fr.pacifista.api.server.jobs.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerBlockPlaceDTO;

public class JobPlayerBlockPlaceClientImpl extends FeignImpl<JobPlayerBlockPlaceDTO, JobPlayerBlockPlaceClient> implements JobPlayerBlockPlaceClient {
    public static final String PATH = "jobs/player-block-place";

    public JobPlayerBlockPlaceClientImpl() {
        super(PATH, JobPlayerBlockPlaceClient.class);
    }
}
