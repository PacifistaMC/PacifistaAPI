package fr.pacifista.api.web.shop.service.payment.resources;

import com.funixproductions.api.payment.paypal.client.clients.PaypalPlanFeignClient;
import com.funixproductions.api.payment.paypal.client.clients.PaypalSubscriptionFeignClient;
import com.funixproductions.api.payment.paypal.client.dtos.requests.paypal.PaypalCreateSubscriptionDTO;
import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalPlanDTO;
import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalSubscriptionDTO;
import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.core.crud.dtos.PageDTO;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkInternalClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PacifistaPlusResourceTest {

    private static final String ROUTE = "/web/shop/pacifistaplus";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserAuthClient userAuthClient;

    @MockitoBean
    private PacifistaWebUserLinkInternalClient pacifistaWebUserLinkInternalClient;

    @MockitoBean
    private PaypalSubscriptionFeignClient subscriptionClient;

    @MockitoBean
    private PaypalPlanFeignClient planClient;

    @BeforeEach
    void setUp() {
        this.generateUser();

        when(this.planClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(
                new PageDTO<>(
                        List.of(
                                new PaypalPlanDTO(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), 10.0, UUID.randomUUID().toString())
                        ), 1, 1, 1L, 1
                )
        );
    }

    @Test
    void testCreateSubscriptionSuccess() throws Exception {
        when(this.subscriptionClient.subscribe(any(PaypalCreateSubscriptionDTO.class))).thenReturn(
                new PaypalSubscriptionDTO(
                        new PaypalPlanDTO(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), 10.0, UUID.randomUUID().toString()),
                        UUID.randomUUID().toString(),
                        UUID.randomUUID(),
                        true,
                        1,
                        new Date(),
                        new Date(),
                        UUID.randomUUID().toString()
                )
        );

        this.mockMvc.perform(
                        post(ROUTE)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());

        this.mockMvc.perform(
                        post(ROUTE)
                                .accept(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                )
                .andExpect(status().isOk());
    }

    @Test
    void testGetSubscriptionSuccess() throws Exception {
        when(this.subscriptionClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(
                new PageDTO<>(List.of(
                        new PaypalSubscriptionDTO(
                                new PaypalPlanDTO(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), 10.0, UUID.randomUUID().toString()),
                                UUID.randomUUID().toString(),
                                UUID.randomUUID(),
                                true,
                                1,
                                new Date(),
                                new Date(),
                                UUID.randomUUID().toString()
                        )
                ), 1, 1, 1L, 1)
        );

        this.mockMvc.perform(
                        get(ROUTE)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());

        this.mockMvc.perform(
                        get(ROUTE)
                                .accept(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                )
                .andExpect(status().isOk());
    }

    @Test
    void testGetSubscriptionNotFoundBecauseNotSubscribed() throws Exception {
        when(this.subscriptionClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(
                new PageDTO<>(List.of(), 1, 1, 1L, 1)
        );

        this.mockMvc.perform(
                        get(ROUTE)
                                .accept(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testPauseSubscriptionSuccess() throws Exception {
        final PaypalSubscriptionDTO subscriptionDTO = new PaypalSubscriptionDTO(
                new PaypalPlanDTO(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), 10.0, UUID.randomUUID().toString()),
                UUID.randomUUID().toString(),
                UUID.randomUUID(),
                true,
                1,
                new Date(),
                new Date(),
                UUID.randomUUID().toString()
        );
        subscriptionDTO.setId(UUID.randomUUID());

        when(this.subscriptionClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(
                new PageDTO<>(List.of(
                        subscriptionDTO
                ), 1, 1, 1L, 1)
        );

        this.mockMvc.perform(
                        post(ROUTE + "/pause")
                                .accept(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                )
                .andExpect(status().isOk());
    }

    @Test
    void testPauseSubscriptionWhenNotSubShouldFail() throws Exception {
        when(this.subscriptionClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(
                new PageDTO<>(List.of(), 1, 1, 1L, 1)
        );

        this.mockMvc.perform(
                        post(ROUTE + "/pause")
                                .accept(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testResumeSubscriptionSuccess() throws Exception {
        final PaypalSubscriptionDTO subscriptionDTO = new PaypalSubscriptionDTO(
                new PaypalPlanDTO(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), 10.0, UUID.randomUUID().toString()),
                UUID.randomUUID().toString(),
                UUID.randomUUID(),
                true,
                1,
                new Date(),
                new Date(),
                UUID.randomUUID().toString()
        );
        subscriptionDTO.setId(UUID.randomUUID());

        when(this.subscriptionClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(
                new PageDTO<>(List.of(
                        subscriptionDTO
                ), 1, 1, 1L, 1)
        );

        this.mockMvc.perform(
                        post(ROUTE + "/resume")
                                .accept(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                )
                .andExpect(status().isOk());
    }

    @Test
    void testResumeSubscriptionWhenNotSubShouldFail() throws Exception {
        when(this.subscriptionClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(
                new PageDTO<>(List.of(), 1, 1, 1L, 1)
        );

        this.mockMvc.perform(
                        post(ROUTE + "/resume")
                                .accept(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testCancelSubscriptionSuccess() throws Exception {
        final PaypalSubscriptionDTO subscriptionDTO = new PaypalSubscriptionDTO(
                new PaypalPlanDTO(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), 10.0, UUID.randomUUID().toString()),
                UUID.randomUUID().toString(),
                UUID.randomUUID(),
                true,
                1,
                new Date(),
                new Date(),
                UUID.randomUUID().toString()
        );
        subscriptionDTO.setId(UUID.randomUUID());

        when(this.subscriptionClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(
                new PageDTO<>(List.of(
                        subscriptionDTO
                ), 1, 1, 1L, 1)
        );

        this.mockMvc.perform(
                        post(ROUTE + "/cancel")
                                .accept(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                )
                .andExpect(status().isOk());
    }

    @Test
    void testCancelSubscriptionWhenNotSubShouldFail() throws Exception {
        when(this.subscriptionClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(
                new PageDTO<>(List.of(), 1, 1, 1L, 1)
        );

        this.mockMvc.perform(
                        post(ROUTE + "/cancel")
                                .accept(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + UUID.randomUUID())
                )
                .andExpect(status().isNotFound());
    }

    private void generateUser() {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        final PacifistaWebUserLinkDTO link = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        link.setLinked(true);

        when(this.userAuthClient.current(anyString())).thenReturn(userDTO);
        when(this.pacifistaWebUserLinkInternalClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(
                new PageDTO<>(List.of(link), 1, 0, 1L, 1)
        );
    }

}
