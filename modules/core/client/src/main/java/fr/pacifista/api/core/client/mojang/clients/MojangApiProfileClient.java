package fr.pacifista.api.core.client.mojang.clients;

import fr.pacifista.api.core.client.mojang.dto.MojangUserNameAndIdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "MojangApiProfileClient", url = MojangApiImplClient.MOJANG_MINECRAFT_SERVICES_API_DOMAIN)
public interface MojangApiProfileClient {

    /**
     * <a href="https://wiki.vg/Mojang_API#Usernames_to_UUIDs">Documentation route</a>
     * @param usernames List of usernames
     * @return username and id
     */
    @PostMapping(
            path = "minecraft/profile/lookup/bulk/byname",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<MojangUserNameAndIdDTO> getUserIdByUsername(@RequestBody List<String> usernames);

}
