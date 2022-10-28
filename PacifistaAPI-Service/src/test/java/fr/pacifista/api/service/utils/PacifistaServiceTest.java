package fr.pacifista.api.service.utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.google.common.reflect.TypeToken;
import com.icegreen.greenmail.util.GreenMail;
import fr.funixgaming.api.client.user.dtos.UserDTO;
import fr.funixgaming.api.client.user.dtos.UserTokenDTO;
import fr.funixgaming.api.client.user.enums.UserRole;
import fr.funixgaming.api.core.crud.dtos.ApiDTO;
import lombok.Getter;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Getter
@SpringBootTest
@AutoConfigureMockMvc
@Import({
        MailTestConfig.class,
        WiremockTestServer.class
})
public abstract class PacifistaServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GreenMail greenMail;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private JsonHelper jsonHelper;

    /**
     * Method used to mock the funix api server
     */
    @BeforeEach
    void setupAuth() throws Exception {
        final UserDTO userDTO = new UserDTO();
        userDTO.setCreatedAt(Date.from(Instant.now()));
        userDTO.setEmail("test@pacifista.fr");
        userDTO.setRole(UserRole.ADMIN);
        userDTO.setUsername("testuser");
        userDTO.setId(UUID.randomUUID());

        final UserTokenDTO tokenDTO = new UserTokenDTO();
        tokenDTO.setUser(userDTO);
        tokenDTO.setToken("tokentest");
        tokenDTO.setCreatedAt(Date.from(Instant.now()));
        tokenDTO.setExpirationDate(Date.from(Instant.now().plusSeconds(100000)));
        tokenDTO.setId(UUID.randomUUID());

        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/user/current"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(jsonHelper.toJson(userDTO))
                )
        );
    }

    /**
     * @param route route method
     * @param body dto
     * @param resultMatcher status().isOk()
     * @return dto response
     * @throws Exception when error
     */
    public <DTO extends ApiDTO> DTO sendPostRequest(@NonNull final String route,
                                                    @NonNull final ApiDTO body,
                                                    @NonNull final ResultMatcher resultMatcher,
                                                    @NonNull Class<DTO> type) throws Exception {
        final MvcResult result = mockMvc.perform(post(route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer tokentest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(body))
        ).andExpect(resultMatcher).andReturn();

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
    }

    /**
     * @param route route method
     * @param body dto
     * @param resultMatcher status().isOk()
     * @return dto response
     * @throws Exception when error
     */
    public <DTO extends ApiDTO> DTO sendPatchRequest(@NonNull final String route,
                                                     @NonNull final ApiDTO body,
                                                     @NonNull final ResultMatcher resultMatcher,
                                                     @NonNull Class<DTO> type) throws Exception {
        final MvcResult result = mockMvc.perform(patch(route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer tokentest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(body))
        ).andExpect(resultMatcher).andReturn();

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
    }

    /**
     * @param route route method
     * @param body dto
     * @param resultMatcher status().isOk()
     * @return dto response
     * @throws Exception when error
     */
    public <DTO extends ApiDTO> List<DTO> sendPatchRequestList(@NonNull final String route,
                                                               @NonNull final ApiDTO body,
                                                               @NonNull final ResultMatcher resultMatcher) throws Exception {
        final MvcResult result = mockMvc.perform(patch(route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer tokentest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(body))
        ).andExpect(resultMatcher).andReturn();

        final Type typeList = new TypeToken<ArrayList<DTO>>(){}.getType();
        return jsonHelper.fromJson(result.getResponse().getContentAsString(), typeList);
    }

    /**
     * @param route route method
     * @param resultMatcher status().isOk()
     * @return dto response
     * @throws Exception when error
     */
    public <DTO extends ApiDTO> DTO sendGetRequest(@NonNull final String route,
                                                   @NonNull final ResultMatcher resultMatcher,
                                                   @NonNull Class<DTO> type) throws Exception {
        final MvcResult result = mockMvc.perform(get(route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer tokentest")
        ).andExpect(resultMatcher).andReturn();

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
    }

    /**
     * @param route route method
     * @param resultMatcher status().isOk()
     * @return dto response
     * @throws Exception when error
     */
    public <DTO extends ApiDTO> List<DTO> sendGetRequestList(@NonNull final String route,
                                                             @NonNull final ResultMatcher resultMatcher) throws Exception {
        final MvcResult result = mockMvc.perform(get(route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer tokentest")
        ).andExpect(resultMatcher).andReturn();

        final Type typeList = new TypeToken<ArrayList<DTO>>(){}.getType();
        return jsonHelper.fromJson(result.getResponse().getContentAsString(), typeList);
    }

    /**
     * @param route route method
     * @param resultMatcher status().isOk()
     * @throws Exception when error
     */
    public void sendDeleteRequest(@NonNull final String route,
                                  @NonNull final ResultMatcher resultMatcher) throws Exception {
        mockMvc.perform(delete(route)
                .header(HttpHeaders.AUTHORIZATION, "Bearer tokentest")
        ).andExpect(resultMatcher);
    }
}
