package fr.pacifista.api.core.client.enums.mojang.client;

import fr.pacifista.api.core.client.enums.mojang.dto.MojangUserNameAndIdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MojangAPI", url = MojangApiImplClient.MOJANG_API_DOMAIN)
public interface MojangApiClient {

    /**
     * <a href="https://mojang-api-docs.netlify.app/no-auth/username-to-uuid-get.html">Documentation route</a>
     * @param username Username
     * @return username and id
     */
    @GetMapping("/users/profiles/minecraft/{username}")
    MojangUserNameAndIdDTO getUserIdByUsername(@PathVariable("username") String username);

    /**
     * <a href="https://mojang-api-docs.netlify.app/no-auth/uuid-to-username.html">Documentation route</a>
     * @param uuid Player UUID
     * @return username and id
     */
    @GetMapping("/user/profile/{uuid}")
    MojangUserNameAndIdDTO getUsernameByUuid(@PathVariable("uuid") String uuid);

}
