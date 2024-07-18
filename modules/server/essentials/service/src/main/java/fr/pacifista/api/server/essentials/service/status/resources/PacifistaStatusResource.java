package fr.pacifista.api.server.essentials.service.status.resources;

import com.funixproductions.core.exceptions.ApiException;
import com.funixproductions.core.exceptions.ApiNotFoundException;
import fr.pacifista.api.server.essentials.client.status.clients.PacifistaStatus;
import fr.pacifista.api.server.essentials.client.status.clients.PacifistaStatusImplClient;
import fr.pacifista.api.server.essentials.client.status.dtos.PacifistaServerInfoDTO;
import fr.pacifista.api.server.essentials.service.status.clients.PacifistaInternalStatusClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/" + PacifistaStatusImplClient.PATH)
@Slf4j(topic = "PacifistaStatusResource")
@RequiredArgsConstructor
public class PacifistaStatusResource implements PacifistaStatus {

    private final PacifistaInternalStatusClient pacifistaInternalStatusClient;

    private PacifistaServerInfoDTO pacifistaServerInfoDTO;

    @Override
    public PacifistaServerInfoDTO getServerInfo() {
        if (pacifistaServerInfoDTO == null) {
            throw new ApiNotFoundException("Les données de Pacifista n'ont pas encore été récupérées.");
        } else {
            return pacifistaServerInfoDTO;
        }
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void updateInfos() {
        try {
            pacifistaServerInfoDTO = pacifistaInternalStatusClient.getServerInfo();
        } catch (Exception e) {
            log.error("Impossible de récupérer les données de Pacifista.", new ApiException("Api error on PacifistaStatusResource.updateInfos()", e));
        }
    }
}
