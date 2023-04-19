package fr.pacifista.api.client.sanctions.clients;

import fr.funixgaming.api.core.crud.clients.CrudClient;
import fr.pacifista.api.client.sanctions.dtos.SanctionDTO;
import fr.pacifista.api.client.sanctions.enums.SanctionType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PacifistaSanction", url = "${pacifista.api.app-domain-url}", path = "/sanctions")
public interface SanctionClient extends CrudClient<SanctionDTO> {

    /**
     * Check if the player is sanctioned, if not returns a server 404 code
     * @param playerUuid Mojang player UUID
     * @param sanctionType Sanction type BAN, MUTE, KICK, ...
     * @return SanctionDTO if active sanction
     */
    @GetMapping("isPlayerSanctioned")
    SanctionDTO isPlayerSanctioned(@RequestParam String playerUuid, @RequestParam SanctionType sanctionType);

    /**
     * Check if the ip is sanctioned, if not returns a server 404 code
     * @param playerIp player ip
     * @param sanctionType Sanction type BAN, MUTE, KICK, ...
     * @return SanctionDTO if active sanction
     */
    @GetMapping("isIpSanctioned")
    SanctionDTO isIpSanctioned(@RequestParam String playerIp, @RequestParam SanctionType sanctionType);

}
