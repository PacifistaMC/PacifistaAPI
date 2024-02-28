package fr.pacifista.api.server.jobs.client.clients;

import fr.pacifista.api.core.client.clients.FeignImpl;
import fr.pacifista.api.server.jobs.client.dtos.JobPlayerQuestDTO;

public class JobPlayerQuestClientImpl extends FeignImpl<JobPlayerQuestDTO, JobPlayerQuestClient> implements JobPlayerQuestClient {

    public static final String PATH = "jobs/player-quests";

    public JobPlayerQuestClientImpl() {
        super(PATH, JobPlayerQuestClient.class);
    }
}
