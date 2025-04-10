package fr.pacifista.api.web.shop.service.payment.resources;

import com.funixproductions.api.payment.billing.client.enums.PaymentType;
import com.funixproductions.api.payment.paypal.client.clients.PaypalOrderFeignClient;
import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalOrderDTO;
import com.funixproductions.api.payment.paypal.client.dtos.responses.PaypalPlanDTO;
import com.funixproductions.api.payment.paypal.client.enums.OrderStatus;
import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import com.funixproductions.core.tools.pdf.tools.VATInformation;
import fr.pacifista.api.core.client.enums.ServerType;
import fr.pacifista.api.server.essentials.client.commands_sender.clients.CommandToSendInternalClient;
import fr.pacifista.api.server.essentials.client.commands_sender.dtos.CommandToSendDTO;
import fr.pacifista.api.server.players.data.client.clients.PacifistaPlayerDataInternalClient;
import fr.pacifista.api.server.players.data.client.dtos.PacifistaPlayerDataDTO;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentRequestDTO;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentResponseDTO;
import fr.pacifista.api.web.shop.service.articles.entities.ShopArticle;
import fr.pacifista.api.web.shop.service.articles.repositories.ShopArticleRepository;
import fr.pacifista.api.web.shop.service.categories.entities.ShopCategory;
import fr.pacifista.api.web.shop.service.categories.repositories.ShopCategoryRepository;
import fr.pacifista.api.web.shop.service.payment.repositories.ShopArticlePurchaseRepository;
import fr.pacifista.api.web.shop.service.payment.repositories.ShopPaymentRepository;
import fr.pacifista.api.web.shop.service.payment.services.PacifistaPlusService;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkInternalClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShopPaymentResourceTest {

    private static final String ROUTE = "/web/shop/";

    private final List<ShopArticle> articles = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShopArticleRepository shopArticleRepository;

    @Autowired
    private ShopCategoryRepository shopCategoryRepository;

    @Autowired
    private ShopArticlePurchaseRepository shopArticlePurchaseRepository;

    @Autowired
    private ShopPaymentRepository shopPaymentRepository;

    @Autowired
    private JsonHelper jsonHelper;

    @MockitoBean
    private UserAuthClient authClient;

    @MockitoBean
    private PaypalOrderFeignClient paypalOrderFeignClient;

    @MockitoBean
    private PacifistaWebUserLinkInternalClient pacifistaWebUserLinkInternalClient;

    @MockitoBean
    private PacifistaPlayerDataInternalClient pacifistaPlayerDataInternalClient;

    @MockitoBean
    private CommandToSendInternalClient commandToSendInternalClient;

    @MockitoBean
    PacifistaPlusService pacifistaPlusService;

    @BeforeEach
    void mockPacifistaPlusService() {
        when(pacifistaPlusService.getPlan()).thenReturn(new PaypalPlanDTO());
    }

    @Test
    void testCardOrder() throws Exception {
        final PacifistaShopPaymentRequestDTO pacifistaShopPaymentRequestDTO = new PacifistaShopPaymentRequestDTO(
                generateArticles(),
                new PacifistaShopPaymentRequestDTO.CreditCard(
                        "holder",
                        "testCardNumber",
                        "123",
                        10,
                        2024
                )
        );

        this.mockMvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaShopPaymentRequestDTO))
                )
                .andExpect(status().isUnauthorized());
        MvcResult user1Res = this.mockMvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaShopPaymentRequestDTO))
                        .header("Authorization", "Bearer user1")
                )
                .andExpect(status().isOk())
                .andReturn();

        final PaypalOrderDTO cardOrderDTO = new PaypalOrderDTO();
        cardOrderDTO.setOrderId(UUID.randomUUID().toString());
        cardOrderDTO.setStatus(OrderStatus.COMPLETED);
        cardOrderDTO.setCreateTime("2021-08-01T00:00:00Z");
        cardOrderDTO.setUpdateTime("2021-08-01T00:00:00Z");
        cardOrderDTO.setCreditCardPayment(true);
        cardOrderDTO.setPurchaseUnitDTOS(new ArrayList<>());
        cardOrderDTO.setVatInformation(VATInformation.FRANCE);
        cardOrderDTO.setUrlClientRedirection("http://localhost:8080");
        when(paypalOrderFeignClient.createCardOrder(any())).thenReturn(cardOrderDTO);
        when(paypalOrderFeignClient.getOrder(cardOrderDTO.getOrderId())).thenReturn(cardOrderDTO);
        when(paypalOrderFeignClient.captureOrder(cardOrderDTO.getOrderId())).thenReturn(cardOrderDTO);

        MvcResult user2res = this.mockMvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaShopPaymentRequestDTO))
                        .header("Authorization", "Bearer user2")
                )
                .andExpect(status().isOk())
                .andReturn();
        final PacifistaShopPaymentResponseDTO user1Response = jsonHelper.fromJson(user1Res.getResponse().getContentAsString(), PacifistaShopPaymentResponseDTO.class);
        final PacifistaShopPaymentResponseDTO user2Response = jsonHelper.fromJson(user2res.getResponse().getContentAsString(), PacifistaShopPaymentResponseDTO.class);
        assertEquals(PaymentType.CREDIT_CARD, user1Response.getPaymentType());
        assertEquals(PaymentType.CREDIT_CARD, user2Response.getPaymentType());

        this.mockMvc.perform(get(ROUTE)
                        .param("paymentExternalOrderId", user1Response.getPaymentExternalOrderId())
                )
                .andExpect(status().isUnauthorized());
        this.mockMvc.perform(get(ROUTE)
                        .header("Authorization", "Bearer user1")
                        .param("paymentExternalOrderId", user1Response.getPaymentExternalOrderId())
                )
                .andExpect(status().isOk());
        this.mockMvc.perform(get(ROUTE)
                        .header("Authorization", "Bearer user2")
                        .param("paymentExternalOrderId", user1Response.getPaymentExternalOrderId())
                )
                .andExpect(status().isForbidden());
        this.mockMvc.perform(get(ROUTE)
                        .header("Authorization", "Bearer user1")
                        .param("paymentExternalOrderId", user2Response.getPaymentExternalOrderId())
                )
                .andExpect(status().isForbidden());
        this.mockMvc.perform(post(ROUTE + "/capture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paymentExternalOrderId", user1Response.getPaymentExternalOrderId())
                )
                .andExpect(status().isUnauthorized());
        this.mockMvc.perform(post(ROUTE + "/capture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paymentExternalOrderId", user1Response.getPaymentExternalOrderId())
                        .header("Authorization", "Bearer user1")
                )
                .andExpect(status().isOk());
        this.mockMvc.perform(post(ROUTE + "/capture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paymentExternalOrderId", user1Response.getPaymentExternalOrderId())
                        .header("Authorization", "Bearer user2")
                )
                .andExpect(status().isForbidden());
        this.mockMvc.perform(post(ROUTE + "/capture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paymentExternalOrderId", user2Response.getPaymentExternalOrderId())
                        .header("Authorization", "Bearer user1")
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void testPaypalOrder() throws Exception {
        final PacifistaShopPaymentRequestDTO pacifistaShopPaymentRequestDTO = new PacifistaShopPaymentRequestDTO(
                generateArticles(),
                null
        );

        this.mockMvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaShopPaymentRequestDTO))
                )
                .andExpect(status().isUnauthorized());
        MvcResult user1Res = this.mockMvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaShopPaymentRequestDTO))
                        .header("Authorization", "Bearer user1")
                )
                .andExpect(status().isOk())
                .andReturn();

        final PaypalOrderDTO paypalOrderDTO = new PaypalOrderDTO();
        paypalOrderDTO.setOrderId(UUID.randomUUID().toString());
        paypalOrderDTO.setStatus(OrderStatus.COMPLETED);
        paypalOrderDTO.setCreateTime("2021-08-01T00:00:00Z");
        paypalOrderDTO.setUpdateTime("2021-08-01T00:00:00Z");
        paypalOrderDTO.setCreditCardPayment(false);
        paypalOrderDTO.setPurchaseUnitDTOS(new ArrayList<>());
        paypalOrderDTO.setVatInformation(VATInformation.FRANCE);
        paypalOrderDTO.setUrlClientRedirection("http://localhost:8080");
        when(paypalOrderFeignClient.createPaypalOrder(any())).thenReturn(paypalOrderDTO);
        when(paypalOrderFeignClient.getOrder(paypalOrderDTO.getOrderId())).thenReturn(paypalOrderDTO);
        when(paypalOrderFeignClient.captureOrder(paypalOrderDTO.getOrderId())).thenReturn(paypalOrderDTO);

        MvcResult user2res = this.mockMvc.perform(post(ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHelper.toJson(pacifistaShopPaymentRequestDTO))
                        .header("Authorization", "Bearer user2")
                )
                .andExpect(status().isOk())
                .andReturn();

        final PacifistaShopPaymentResponseDTO user1Response = jsonHelper.fromJson(user1Res.getResponse().getContentAsString(), PacifistaShopPaymentResponseDTO.class);
        final PacifistaShopPaymentResponseDTO user2Response = jsonHelper.fromJson(user2res.getResponse().getContentAsString(), PacifistaShopPaymentResponseDTO.class);
        assertEquals(PaymentType.PAYPAL, user1Response.getPaymentType());
        assertEquals(PaymentType.PAYPAL, user2Response.getPaymentType());

        this.mockMvc.perform(get(ROUTE)
                        .param("paymentExternalOrderId", user1Response.getPaymentExternalOrderId())
                )
                .andExpect(status().isUnauthorized());
        this.mockMvc.perform(get(ROUTE)
                        .header("Authorization", "Bearer user1")
                        .param("paymentExternalOrderId", user1Response.getPaymentExternalOrderId())
                )
                .andExpect(status().isOk());
        this.mockMvc.perform(get(ROUTE)
                        .header("Authorization", "Bearer user2")
                        .param("paymentExternalOrderId", user1Response.getPaymentExternalOrderId())
                )
                .andExpect(status().isForbidden());
        this.mockMvc.perform(get(ROUTE)
                        .header("Authorization", "Bearer user1")
                        .param("paymentExternalOrderId", user2Response.getPaymentExternalOrderId())
                )
                .andExpect(status().isForbidden());
        this.mockMvc.perform(post(ROUTE + "/capture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paymentExternalOrderId", user1Response.getPaymentExternalOrderId())
                )
                .andExpect(status().isUnauthorized());
        this.mockMvc.perform(post(ROUTE + "/capture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paymentExternalOrderId", user1Response.getPaymentExternalOrderId())
                        .header("Authorization", "Bearer user1")
                )
                .andExpect(status().isOk());
        this.mockMvc.perform(post(ROUTE + "/capture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paymentExternalOrderId", user1Response.getPaymentExternalOrderId())
                        .header("Authorization", "Bearer user2")
                )
                .andExpect(status().isForbidden());
        this.mockMvc.perform(post(ROUTE + "/capture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("paymentExternalOrderId", user2Response.getPaymentExternalOrderId())
                        .header("Authorization", "Bearer user1")
                )
                .andExpect(status().isForbidden());
    }

    private List<PacifistaShopPaymentRequestDTO.ShopArticleRequest> generateArticles() {
        final List<PacifistaShopPaymentRequestDTO.ShopArticleRequest> shopArticleRequests = new ArrayList<>();
        final Random random = new Random();

        for (ShopArticle article : articles) {
            shopArticleRequests.add(new PacifistaShopPaymentRequestDTO.ShopArticleRequest(
                    article.getUuid().toString(),
                    Math.abs(random.nextInt(10) + 1)
            ));
        }
        return shopArticleRequests;
    }

    @BeforeEach
    void setupMocks() {
        final UserDTO userDTO = new UserDTO();
        userDTO.setId(UUID.randomUUID());
        userDTO.setCreatedAt(new Date());
        userDTO.setUsername("testUser");
        userDTO.setEmail("test@gmail.com");
        userDTO.setRole(UserRole.USER);
        userDTO.setValid(true);
        userDTO.setCountry(new UserDTO.Country(
                "France",
                10,
                "FR",
                "FRA"
        ));

        final UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(UUID.randomUUID());
        userDTO2.setCreatedAt(new Date());
        userDTO2.setUsername("testUser2");
        userDTO2.setEmail("test2@gmail.com");
        userDTO2.setRole(UserRole.USER);
        userDTO2.setValid(true);
        userDTO2.setCountry(new UserDTO.Country(
                "France",
                10,
                "FR",
                "FRA"
        ));

        final PaypalOrderDTO paypalOrderDTO = new PaypalOrderDTO();
        paypalOrderDTO.setOrderId(UUID.randomUUID().toString());
        paypalOrderDTO.setStatus(OrderStatus.COMPLETED);
        paypalOrderDTO.setCreateTime("2021-08-01T00:00:00Z");
        paypalOrderDTO.setUpdateTime("2021-08-01T00:00:00Z");
        paypalOrderDTO.setCreditCardPayment(false);
        paypalOrderDTO.setPurchaseUnitDTOS(new ArrayList<>());
        paypalOrderDTO.setVatInformation(VATInformation.FRANCE);
        paypalOrderDTO.setUrlClientRedirection("http://localhost:8080");

        final PaypalOrderDTO cardOrderDTO = new PaypalOrderDTO();
        cardOrderDTO.setOrderId(UUID.randomUUID().toString());
        cardOrderDTO.setStatus(OrderStatus.COMPLETED);
        cardOrderDTO.setCreateTime("2021-08-01T00:00:00Z");
        cardOrderDTO.setUpdateTime("2021-08-01T00:00:00Z");
        cardOrderDTO.setCreditCardPayment(true);
        cardOrderDTO.setPurchaseUnitDTOS(new ArrayList<>());
        cardOrderDTO.setVatInformation(VATInformation.FRANCE);
        cardOrderDTO.setUrlClientRedirection("http://localhost:8080");

        reset(authClient);
        reset(paypalOrderFeignClient);
        when(authClient.current("Bearer user1")).thenReturn(userDTO);
        when(authClient.current("Bearer user2")).thenReturn(userDTO2);
        when(paypalOrderFeignClient.createCardOrder(any())).thenReturn(cardOrderDTO);
        when(paypalOrderFeignClient.createPaypalOrder(any())).thenReturn(paypalOrderDTO);
        when(paypalOrderFeignClient.getOrder(paypalOrderDTO.getOrderId())).thenReturn(paypalOrderDTO);
        when(paypalOrderFeignClient.getOrder(cardOrderDTO.getOrderId())).thenReturn(cardOrderDTO);
        when(paypalOrderFeignClient.captureOrder(paypalOrderDTO.getOrderId())).thenReturn(paypalOrderDTO);
        when(paypalOrderFeignClient.captureOrder(cardOrderDTO.getOrderId())).thenReturn(cardOrderDTO);

        ShopCategory category = new ShopCategory();
        category.setName("testCategory");
        category.setDescription("testDescription");
        category.setMultiPurchaseAllowed(false);
        this.shopArticlePurchaseRepository.deleteAll();
        this.shopPaymentRepository.deleteAll();
        this.shopArticleRepository.deleteAll();
        this.shopCategoryRepository.deleteAll();
        category = this.shopCategoryRepository.save(category);

        this.articles.clear();
        final List<ShopArticle> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final ShopArticle article = new ShopArticle();
            final Random random = new Random();

            article.setCategory(category);
            article.setName("testArticle" + i);
            article.setDescription("testDescription" + i);
            article.setHtmlDescription("testHtmlDescription" + i);
            article.setPrice(random.nextDouble(300) * 100);
            article.setCommandExecuted("testCommandExecuted" + i);
            article.setFileExtension("jpg");
            article.setFileName("testFileName" + i);
            article.setFilePath("testFileUrl" + i);
            article.setFileSize(1000L + i);

            articles.add(article);
        }
        this.articles.addAll(this.shopArticleRepository.saveAll(articles));

        final PacifistaWebUserLinkDTO pacifistaWebUserLinkDTO = new PacifistaWebUserLinkDTO(UUID.randomUUID(), UUID.randomUUID());
        pacifistaWebUserLinkDTO.setLinked(true);
        when(pacifistaWebUserLinkInternalClient.getAll(any(), any(), any(), any())).thenReturn(new PageDTO<>(List.of(pacifistaWebUserLinkDTO), 1, 1, 1L, 1));

        final PacifistaPlayerDataDTO pacifistaPlayerDataDTO = new PacifistaPlayerDataDTO();
        pacifistaPlayerDataDTO.setId(UUID.randomUUID());
        pacifistaPlayerDataDTO.setMinecraftUsername("testMinecraftUsername");
        pacifistaPlayerDataDTO.setMinecraftUuid(UUID.randomUUID());
        when(pacifistaPlayerDataInternalClient.getAll(any(), any(), any(), any())).thenReturn(new PageDTO<>(List.of(pacifistaPlayerDataDTO), 1, 1, 1L, 1));

        when(commandToSendInternalClient.create(anyList())).thenReturn(List.of(
                new CommandToSendDTO(
                        "testCommand",
                        ServerType.SURVIE_ALPHA,
                        false,
                        "testSpringBoot"
                )
        ));
        doNothing().when(commandToSendInternalClient).delete(anyString());
        doNothing().when(commandToSendInternalClient).delete(any(String[].class));
    }

}
