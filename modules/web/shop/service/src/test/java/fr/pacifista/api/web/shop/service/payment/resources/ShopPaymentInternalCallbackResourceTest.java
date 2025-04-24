package fr.pacifista.api.web.shop.service.payment.resources;

import com.funixproductions.api.payment.paypal.client.clients.PaypalSubscriptionFeignClient;
import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalPlanDTO;
import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalSubscriptionDTO;
import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.server.essentials.client.pacifista_plus.clients.PacifistaPlusSubscriptionClient;
import fr.pacifista.api.server.essentials.client.pacifista_plus.dtos.PacifistaPlusSubscriptionDTO;
import fr.pacifista.api.web.shop.service.payment.services.PacifistaPlusService;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkInternalClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShopPaymentInternalCallbackResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockitoBean
    private UserAuthClient userAuthClient;

    @MockitoBean
    private PacifistaWebUserLinkInternalClient pacifistaWebUserLinkInternalClient;

    @MockitoBean
    private PaypalSubscriptionFeignClient subscriptionClient;

    @MockitoBean
    private PacifistaPlusSubscriptionClient pacifistaPlusSubscriptionClient;

    @BeforeEach
    void setupMocks() {
        this.generateUser();
        reset(this.pacifistaPlusSubscriptionClient);
    }

    @Test
    void testWebhookOnUserHasAlreadySubOneMonth() throws Exception {
        final Date expirationDate = new Date();
        final PacifistaPlusSubscriptionDTO subscriptionDTO = new PacifistaPlusSubscriptionDTO(UUID.randomUUID(), 1, expirationDate);
        subscriptionDTO.setId(UUID.randomUUID());

        when(this.pacifistaPlusSubscriptionClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(
                new PageDTO<>(
                        List.of(subscriptionDTO),
                        1, 1, 1L, 1
                )
        );

        subscriptionDTO.setMonths(2);
        when(this.pacifistaPlusSubscriptionClient.update(subscriptionDTO)).thenReturn(subscriptionDTO);

        this.mockMvc.perform(
                post("/kubeinternal/web/shop/cb/paypal-subscription")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonHelper.toJson(new PaypalSubscriptionDTO(
                                new PaypalPlanDTO(UUID.randomUUID().toString(), PacifistaPlusService.SUBSCRIPTION_NAME, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), 10.0, PacifistaPlusService.PROJECT_NAME),
                                UUID.randomUUID().toString(),
                                UUID.randomUUID(),
                                true,
                                1,
                                new Date(),
                                expirationDate,
                                UUID.randomUUID().toString()
                        )))
        ).andExpect(status().isOk());

        verify(this.pacifistaPlusSubscriptionClient, Mockito.times(1)).update(subscriptionDTO);
    }

    @Test
    void testWebhookOnUserHasNoSubscription() throws Exception {
        final Date expirationDate = new Date();

        when(this.pacifistaPlusSubscriptionClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(
                new PageDTO<>(
                        List.of(),
                        1, 1, 1L, 1
                )
        );

        when(this.pacifistaPlusSubscriptionClient.create(any(PacifistaPlusSubscriptionDTO.class))).thenReturn(new PacifistaPlusSubscriptionDTO());

        this.mockMvc.perform(
                post("/kubeinternal/web/shop/cb/paypal-subscription")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonHelper.toJson(new PaypalSubscriptionDTO(
                                new PaypalPlanDTO(UUID.randomUUID().toString(), PacifistaPlusService.SUBSCRIPTION_NAME, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), 10.0, PacifistaPlusService.PROJECT_NAME),
                                UUID.randomUUID().toString(),
                                UUID.randomUUID(),
                                true,
                                1,
                                new Date(),
                                expirationDate,
                                UUID.randomUUID().toString()
                        )))
        ).andExpect(status().isOk());

        verify(this.pacifistaPlusSubscriptionClient, Mockito.times(1)).create(any(PacifistaPlusSubscriptionDTO.class));
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
