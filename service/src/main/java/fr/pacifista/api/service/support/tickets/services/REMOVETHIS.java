package fr.pacifista.api.service.support.tickets.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "REMOVETHIS", url = "https://fcm.googleapis.com", path = "/fcm/send")
public interface REMOVETHIS {

    @PostMapping
    void sendNotification(@RequestHeader("Authorization") String fcmToken,
                          @RequestBody REMOVEDTO body);

}
