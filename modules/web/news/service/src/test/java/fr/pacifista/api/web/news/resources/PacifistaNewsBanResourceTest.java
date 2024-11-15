package fr.pacifista.api.web.news.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.web.news.client.dtos.ban.PacifistaNewsBanDTO;
import fr.pacifista.api.web.news.service.PacifistaWebNewsApp;
import fr.pacifista.api.web.news.service.repositories.ban.PacifistaNewsBanRepository;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkInternalClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PacifistaWebNewsApp.class)
@AutoConfigureMockMvc
class PacifistaNewsBanResourceTest {

    private static final String BASE_ROUTE = "/web/news/bans";

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PacifistaNewsBanRepository repository;

    @MockBean
    private UserAuthClient authClient;

    @MockBean
    private PacifistaWebUserLinkInternalClient pacifistaLinkClient;

    @BeforeEach
    void resetDatabase() {
        this.repository.deleteAll();
    }

    @Test
    void testCrud() throws Exception {
        final UserDTO mockUser = generateUserDtoMock(UserRole.PACIFISTA_MODERATOR);
        final PacifistaWebUserLinkDTO mockUserMinecraftLink = generatePacifistaLinkMock(mockUser);

        setUserDtoMock(mockUser);
        setPacifistaLinkMock(mockUserMinecraftLink);

        final PacifistaNewsBanDTO request = new PacifistaNewsBanDTO(UUID.randomUUID(), "bannedMinecraft" + UUID.randomUUID(), "test" + UUID.randomUUID());

        final PacifistaNewsBanDTO created = createRequest(request, false);
        assertNotNull(created);
        assertEquals(request.getMinecraftUserNameBanned(), created.getMinecraftUserNameBanned());
        assertEquals(request.getFunixProdUserIdBanned(), created.getFunixProdUserIdBanned());
        assertEquals(mockUserMinecraftLink.getMinecraftUsername(), created.getStaffMinecraftUserName());
        assertEquals(mockUser.getId(), created.getStaffFunixProdUserId());
        assertEquals(request.getReason(), created.getReason());

        createRequest(request, true);
        createRequest(
                new PacifistaNewsBanDTO(UUID.randomUUID(), request.getMinecraftUserNameBanned(), "test" + UUID.randomUUID()),
                true
        );
        createRequest(
                new PacifistaNewsBanDTO(request.getFunixProdUserIdBanned(), request.getMinecraftUserNameBanned(), "test" + UUID.randomUUID()),
                true
        );
        createRequest(
                new PacifistaNewsBanDTO(request.getFunixProdUserIdBanned(), UUID.randomUUID().toString(), "test" + UUID.randomUUID()),
                true
        );
        createRequest(
                new PacifistaNewsBanDTO(UUID.randomUUID(), request.getMinecraftUserNameBanned().toUpperCase(), "test" + UUID.randomUUID()),
                true
        );
        createRequest(
                new PacifistaNewsBanDTO(UUID.randomUUID(), request.getMinecraftUserNameBanned().toLowerCase(), "test" + UUID.randomUUID()),
                true
        );

        created.setReason("test2" + UUID.randomUUID());
        final PacifistaNewsBanDTO patched = patchRequest(created, false);
        assertNotNull(patched);
        assertEquals(created.getId(), patched.getId());
        assertEquals(created.getFunixProdUserIdBanned(), patched.getFunixProdUserIdBanned());
        assertEquals(created.getMinecraftUserNameBanned(), patched.getMinecraftUserNameBanned());
        assertEquals(created.getStaffFunixProdUserId(), patched.getStaffFunixProdUserId());
        assertEquals(created.getStaffMinecraftUserName(), patched.getStaffMinecraftUserName());
        assertEquals(created.getReason(), patched.getReason());

        patched.setReason("test3" + UUID.randomUUID());
        final PacifistaNewsBanDTO updated = putRequest(patched, false);
        assertNotNull(updated);
        assertEquals(patched.getId(), updated.getId());
        assertEquals(patched.getFunixProdUserIdBanned(), updated.getFunixProdUserIdBanned());
        assertEquals(patched.getMinecraftUserNameBanned(), updated.getMinecraftUserNameBanned());
        assertEquals(patched.getStaffFunixProdUserId(), updated.getStaffFunixProdUserId());
        assertEquals(patched.getStaffMinecraftUserName(), updated.getStaffMinecraftUserName());
        assertEquals(patched.getReason(), updated.getReason());

        deleteRequest(UUID.randomUUID(), true);
        deleteRequest(created.getId(), false);
    }

    @Test
    void testCreateWithNoFunixProdId() throws Exception {
        final UserDTO mockUser = generateUserDtoMock(UserRole.USER);
        final PacifistaWebUserLinkDTO mockUserMinecraftLink = generatePacifistaLinkMock(mockUser);

        setUserDtoMock(mockUser);
        setPacifistaLinkMock(mockUserMinecraftLink);

        final PacifistaNewsBanDTO request = new PacifistaNewsBanDTO(null, "test" + UUID.randomUUID());
        createRequest(request, true);

        mockUser.setRole(UserRole.PACIFISTA_MODERATOR);
        setUserDtoMock(mockUser);

        createRequest(request, true);
        request.setId(UUID.randomUUID());
        putRequest(request, true);
        patchRequest(request, true);
        deleteRequest(UUID.randomUUID(), true);
    }

    @Test
    void testCreateWithNoReasonSuccess() throws Exception {
        final UserDTO mockUser = generateUserDtoMock(UserRole.ADMIN);
        final PacifistaWebUserLinkDTO mockUserMinecraftLink = generatePacifistaLinkMock(mockUser);

        setUserDtoMock(mockUser);
        setPacifistaLinkMock(mockUserMinecraftLink);

        final PacifistaNewsBanDTO response = createRequest(new PacifistaNewsBanDTO(mockUser.getId(), UUID.randomUUID().toString(), null), false);
        assertNotNull(response);
        assertNull(response.getReason());
    }

    private UserDTO generateUserDtoMock(final UserRole role) {
        final UserDTO mockUser = UserDTO.generateFakeDataForTestingPurposes();
        mockUser.setRole(role);

        return mockUser;
    }

    private PacifistaWebUserLinkDTO generatePacifistaLinkMock(final UserDTO mockUser) {
        final PacifistaWebUserLinkDTO mockUserMinecraftLink = new PacifistaWebUserLinkDTO(mockUser.getId(), UUID.randomUUID());
        mockUserMinecraftLink.setMinecraftUsername("mockUserMinecraft" + UUID.randomUUID());
        mockUserMinecraftLink.setLinked(true);
        mockUserMinecraftLink.setCreatedAt(new Date());
        mockUserMinecraftLink.setId(UUID.randomUUID());

        return mockUserMinecraftLink;
    }

    private void setUserDtoMock(final UserDTO userDTO) {
        when(this.authClient.current(anyString())).thenReturn(userDTO);
    }

    private void setPacifistaLinkMock(final PacifistaWebUserLinkDTO linkDTO) {
        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(linkDTO), 1, 0, 0L, 0
        ));
    }

    private PacifistaNewsBanDTO createRequest(final PacifistaNewsBanDTO request, final boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(post(BASE_ROUTE)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(this.jsonHelper.toJson(request))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
            return null;
        }

        return this.jsonHelper.fromJson(
                this.mockMvc.perform(post(BASE_ROUTE)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.jsonHelper.toJson(request))
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                PacifistaNewsBanDTO.class
        );
    }

    private PacifistaNewsBanDTO patchRequest(final PacifistaNewsBanDTO request, final boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(patch(BASE_ROUTE)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(this.jsonHelper.toJson(request))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
            return null;
        }

        return this.jsonHelper.fromJson(
                this.mockMvc.perform(patch(BASE_ROUTE)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.jsonHelper.toJson(request))
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                PacifistaNewsBanDTO.class
        );
    }

    private PacifistaNewsBanDTO putRequest(final PacifistaNewsBanDTO request, final boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(put(BASE_ROUTE)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(this.jsonHelper.toJson(request))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
            return null;
        }

        return this.jsonHelper.fromJson(
                this.mockMvc.perform(put(BASE_ROUTE)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(this.jsonHelper.toJson(request))
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                PacifistaNewsBanDTO.class
        );
    }

    private void deleteRequest(final UUID id, final boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(delete(BASE_ROUTE + "?id=" + id)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
            return;
        }

        this.mockMvc.perform(delete(BASE_ROUTE + "?id=" + id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        this.mockMvc.perform(delete(BASE_ROUTE + "?id=" + id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
