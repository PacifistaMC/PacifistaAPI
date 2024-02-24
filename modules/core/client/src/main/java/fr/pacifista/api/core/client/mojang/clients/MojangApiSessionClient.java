package fr.pacifista.api.core.client.mojang.clients;

import fr.pacifista.api.core.client.mojang.dto.MojangUserNameAndIdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MojangAPIProfileClient", url = MojangApiImplClient.MOJANG_SESSION_SERVER_API_DOMAIN)
public interface MojangApiSessionClient {

    /**
     * <a href="https://wiki.vg/Mojang_API#UUID_to_Profile_and_Skin.2FCape">Documentation route</a>
     * @param uuid Player UUID
     * @return username and id
     */
    @GetMapping("session/minecraft/profile/{uuid}")
    MojangUserNameAndIdDTO getUsernameByUuid(@PathVariable("uuid") String uuid);

}
