package fr.pacifista.api.web.vote.service.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import com.google.gson.reflect.TypeToken;
import fr.pacifista.api.server.essentials.client.commands_sender.clients.CommandToSendInternalClient;
import fr.pacifista.api.server.essentials.client.commands_sender.dtos.CommandToSendDTO;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkInternalClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import fr.pacifista.api.web.vote.client.clients.VoteClientImpl;
import fr.pacifista.api.web.vote.client.dtos.VoteDTO;
import fr.pacifista.api.web.vote.client.dtos.VoteWebsiteDTO;
import fr.pacifista.api.web.vote.client.dtos.VotesCountDTO;
import fr.pacifista.api.web.vote.client.enums.VoteWebsite;
import fr.pacifista.api.web.vote.service.entities.Vote;
import fr.pacifista.api.web.vote.service.repositories.VoteRepository;
import fr.pacifista.api.web.vote.service.services.GoogleCaptchaServiceChecker;
import fr.pacifista.api.web.vote.service.services.VoteCheckerService;
import fr.pacifista.api.web.vote.service.services.VoteCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VoteResourceTest {

    private final String BASE_URL = "/" + VoteClientImpl.PATH;
    private final Type VOTE_DTO_TYPE = new TypeToken<PageDTO<VoteDTO>>() {}.getType();

    @Autowired
    JsonHelper jsonHelper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    VoteRepository voteRepository;

    @MockitoBean
    GoogleCaptchaServiceChecker googleCaptchaServiceChecker;

    @MockitoBean
    VoteCheckerService voteCheckerService;

    @MockitoBean
    CommandToSendInternalClient commandToSendClient;

    @MockitoBean
    UserAuthClient userAuthClient;

    @MockitoBean
    PacifistaWebUserLinkInternalClient pacifistaWebUserLinkInternalClient;

    @Autowired
    VoteCrudService voteCrudService;

    @BeforeEach
    void mockClients() {
        this.voteRepository.deleteAll();

        reset(this.userAuthClient);

        when(commandToSendClient.create(any(CommandToSendDTO.class))).thenReturn(new CommandToSendDTO());
        when(commandToSendClient.create(anyList())).thenReturn(new ArrayList<>());
        when(voteCheckerService.hasVoted(any(), any())).thenReturn(true);
        doNothing().when(googleCaptchaServiceChecker).checkCaptcha();
    }

    @Test
    void testCreateVoteWithSiteInvalidFail() throws Exception {
        this.mockMvc.perform(post(BASE_URL + "/user/dummyNameShouldFail"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateVoteWithSiteInvalidAndUsernameFail() throws Exception {
        this.mockMvc.perform(post(BASE_URL + "/user/dummyNameShouldFail?username=popol"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateVoteWithSiteButNoUsernameFail() throws Exception {
        this.mockMvc.perform(post(BASE_URL + "/user/" + VoteWebsite.SERVEUR_PRIVE_NET.name()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCheckVoteWithInvalidSiteName() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/user/dummyNameShouldFail"))
                .andExpect(status().isBadRequest());
    }

    //@Test
    void testCreateAndCheckVote() throws Exception {
        final String username = "funixLeGaming";
        final VoteWebsite voteWebsite = VoteWebsite.SERVEUR_MINECRAFT_ORG;

        this.mockMvc.perform(post(BASE_URL + "/user/" + voteWebsite.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        when(userAuthClient.current(anyString())).thenReturn(userDTO);
        this.setLinkPacifistaWeb(null);

        this.mockMvc.perform(post(BASE_URL + "/user/" + voteWebsite.name())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        final PacifistaWebUserLinkDTO userLinkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        userLinkDTO.setLinked(false);
        userLinkDTO.setMinecraftUsername(username);
        this.setLinkPacifistaWeb(userLinkDTO);

        this.mockMvc.perform(post(BASE_URL + "/user/" + voteWebsite.name())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        userLinkDTO.setLinked(true);
        this.setLinkPacifistaWeb(userLinkDTO);

        MvcResult mvcResult = this.mockMvc.perform(post(BASE_URL + "/user/" + voteWebsite.name())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + username)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        VoteDTO voteDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), VoteDTO.class);
        assertEquals(username.toLowerCase(), voteDTO.getUsername());
        assertNull(voteDTO.getVoteValidationDate());
        assertNull(voteDTO.getNextVoteDate());
        assertNull(voteDTO.getUpdatedAt());
        assertEquals(voteWebsite, voteDTO.getVoteWebsite());

        mvcResult = this.mockMvc.perform(post(BASE_URL + "/user/" + voteWebsite.name())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        VoteDTO voteDTO2 = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), VoteDTO.class);
        assertEquals(voteDTO.getId(), voteDTO2.getId());

        userLinkDTO.setMinecraftUsername("otherPlayer");
        this.setLinkPacifistaWeb(userLinkDTO);

        mvcResult = this.mockMvc.perform(post(BASE_URL + "/user/" + voteWebsite.name())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + username)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        voteDTO2 = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), VoteDTO.class);
        assertNotEquals(voteDTO.getId(), voteDTO2.getId());
        assertEquals("otherPlayer".toLowerCase(), voteDTO2.getUsername());

        userLinkDTO.setMinecraftUsername(username);
        this.setLinkPacifistaWeb(userLinkDTO);

        mvcResult = this.mockMvc.perform(post(BASE_URL + "/user/" + VoteWebsite.SERVEUR_PRIVE_NET.name())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        voteDTO2 = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), VoteDTO.class);
        assertNotEquals(voteDTO.getId(), voteDTO2.getId());

        mvcResult = this.mockMvc.perform(get(BASE_URL + "/user/" + voteWebsite.name())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + username)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        voteDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), VoteDTO.class);
        assertEquals(username.toLowerCase(), voteDTO.getUsername());
        assertNull(voteDTO.getVoteValidationDate());
        assertNull(voteDTO.getNextVoteDate());
        assertNotNull(voteDTO.getUpdatedAt());
        assertEquals(voteWebsite, voteDTO.getVoteWebsite());

        Vote vote = this.voteRepository.findByUuid(voteDTO.getId().toString()).orElseThrow();
        vote.setVoteValidationDate(new Date());
        vote.setNextVoteDate(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)));
        this.voteRepository.save(vote);

        mvcResult = this.mockMvc.perform(get(BASE_URL + "/user/" + voteWebsite.name())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        voteDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), VoteDTO.class);
        assertEquals(username.toLowerCase(), voteDTO.getUsername());
        assertNotNull(voteDTO.getVoteValidationDate());
        assertNotNull(voteDTO.getNextVoteDate());
        assertNotNull(voteDTO.getUpdatedAt());

        mvcResult = this.mockMvc.perform(get(BASE_URL + "?username=" + username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PageDTO<VoteDTO> pageDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), VOTE_DTO_TYPE);
        assertEquals(2, pageDTO.getTotalElementsDatabase());
        boolean found = false;
        for (final VoteDTO voteDTOFromDb : pageDTO.getContent()) {
            if (voteDTOFromDb.getId().equals(voteDTO.getId())) {
                found = true;
                break;
            }
        }
        assertTrue(found);

        this.mockMvc.perform(post(BASE_URL + "/user/" + voteWebsite.name())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvcResult = this.mockMvc.perform(get(BASE_URL + "?username=" + username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        pageDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), VOTE_DTO_TYPE);
        assertEquals(3, pageDTO.getTotalElementsDatabase());

        mvcResult = this.mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        pageDTO = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), VOTE_DTO_TYPE);
        assertEquals(4, pageDTO.getTotalElementsDatabase());

        assertDoesNotThrow(() -> this.voteCrudService.checkPendingVotes());
        for (final Vote voteFromDb : this.voteRepository.findAll()) {
            assertNotNull(voteFromDb.getVoteValidationDate());
            assertNotNull(voteFromDb.getNextVoteDate());
        }
    }

    @Test
    void testGetAvailableWebSites() throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + "/websites")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        final List<VoteWebsiteDTO> voteWebsites = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), new TypeToken<List<VoteWebsiteDTO>>() {}.getType());

        int enabledWebs = 0;
        for (final VoteWebsite voteWebsite : VoteWebsite.values()) {
            if (voteWebsite.isEnabled()) {
                ++enabledWebs;
            }
        }
        assertEquals(enabledWebs, voteWebsites.size());
    }

    @Test
    void testTopVotes() throws Exception {
        final String username = "funixVeritablementGaming";

        int currentMonth = 7;
        int currentYear = 2027;

        assertEquals(0, this.getTop(currentMonth, currentYear).size());

        ++currentMonth;
        this.generateDummyVote(username, currentMonth, currentYear, false);
        assertEquals(0, this.getTop(currentMonth, currentYear).size());

        ++currentMonth;
        this.generateDummyVote(username, currentMonth, currentYear, true);
        assertEquals(1, this.getTop(currentMonth, currentYear).size());

        //check if the cache is working
        this.generateDummyVote(username, currentMonth, currentYear, true);
        assertEquals(1, this.getTop(currentMonth, currentYear).size());
    }

    //@Test
    void testGetCheckVotes() throws Exception {
        final String ipAddress = "test";
        final Type type = new TypeToken<List<VoteDTO>>() {}.getType();

        Vote vote = generateDummyVote("funix", 1, 2020, true);
        generateDummyVote("popol", 1, 2020, true);
        generateDummyVote("popol2", 2, 2020, false);

        MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + "/check?usernames=funix,popol")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<VoteDTO> votes = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), type);
        assertEquals(2, votes.size());

        mvcResult = this.mockMvc.perform(get(BASE_URL + "/check?usernames=funix")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        votes = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), type);
        assertEquals(1, votes.size());
        assertEquals(vote.getUsername(), votes.getFirst().getUsername());
        assertEquals(vote.getUuid(), votes.getFirst().getId());

        mvcResult = this.mockMvc.perform(get(BASE_URL + "/check?usernames=popol")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        votes = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), type);
        assertEquals(1, votes.size());
        assertEquals("popol", votes.getFirst().getUsername());

        mvcResult = this.mockMvc.perform(get(BASE_URL + "/check?usernames=popol2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        votes = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), type);
        assertEquals(0, votes.size());

        mvcResult = this.mockMvc.perform(get(BASE_URL + "/check?usernames=wtfRandom")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        votes = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), type);
        assertEquals(0, votes.size());

        vote = generateDummyVote("funix", 1, 2020, true);
        mvcResult = this.mockMvc.perform(get(BASE_URL + "/check?usernames=funix")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        votes = jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), type);
        assertEquals(1, votes.size());
        assertEquals(vote.getUsername(), votes.getFirst().getUsername());
        assertEquals(vote.getUuid(), votes.getFirst().getId());
    }

    @Test
    void testTopVotesRanking() throws Exception {

        final String username = "unJourFunixSeraLeGaming";
        final int userVotesWanted = 10;
        final String otherUsername = "notchParceQueJaiEnvie";
        final int notchWantedVotes = 5;
        final String thirdUsername = "popol";
        final int thirdWantedVotes = 3;

        int currentMonth = 7;
        int currentYear = 2030;

        for (int i = 0; i < userVotesWanted; i++) {
            this.generateDummyVote(username, currentMonth, currentYear, true);
        }
        for (int i = 0; i < notchWantedVotes; i++) {
            this.generateDummyVote(otherUsername, currentMonth, currentYear, true);
        }
        for (int i = 0; i < thirdWantedVotes; i++) {
            this.generateDummyVote(thirdUsername, currentMonth, currentYear, true);
        }

        final List<VotesCountDTO> topVotes = this.getTop(currentMonth, currentYear);
        assertEquals(3, topVotes.size());

        final VotesCountDTO userVotes = topVotes.get(0);
        final VotesCountDTO notchVotes = topVotes.get(1);
        final VotesCountDTO thirdVotes = topVotes.get(2);

        assertEquals(username, userVotes.getUsername());
        assertEquals(userVotesWanted, userVotes.getVotesCount());

        assertEquals(otherUsername, notchVotes.getUsername());
        assertEquals(notchWantedVotes, notchVotes.getVotesCount());

        assertEquals(thirdUsername, thirdVotes.getUsername());
        assertEquals(thirdWantedVotes, thirdVotes.getVotesCount());

        assertEquals(0, this.getTop(currentMonth - 1, currentYear).size());
        assertEquals(0, this.getTop(currentMonth + 1, currentYear).size());
        assertEquals(0, this.getTop(currentMonth, currentYear + 1).size());
        assertEquals(0, this.getTop(currentMonth - 1, currentYear).size());
    }

    private List<VotesCountDTO> getTop(final int month, final int year) throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(get(BASE_URL + "/top?month=" + month + "&year=" + year)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return jsonHelper.fromJson(mvcResult.getResponse().getContentAsString(), new TypeToken<List<VotesCountDTO>>() {}.getType());
    }

    private Vote generateDummyVote(final String username, final int month, final int year, boolean valid) {
        final Vote vote = new Vote();

        vote.setUsername(username);
        vote.setMonthVote(month);
        vote.setYearVote(year);

        if (valid) {
            vote.setNextVoteDate(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)));
            vote.setVoteValidationDate(Date.from(Instant.now()));
        }

        vote.setVoteWebsite(VoteWebsite.SERVEUR_MINECRAFT_ORG);
        return this.voteRepository.saveAndFlush(vote);
    }

    private void setLinkPacifistaWeb(PacifistaWebUserLinkDTO linkPacifistaWeb) {
        reset(pacifistaWebUserLinkInternalClient);

        final List<PacifistaWebUserLinkDTO> linkDTOs = new ArrayList<>();
        if (linkPacifistaWeb != null) {
            linkDTOs.add(linkPacifistaWeb);
        }

        final PageDTO<PacifistaWebUserLinkDTO> pageDTO = new PageDTO<>(linkDTOs, 2, 0, 10l, 10);
        when(pacifistaWebUserLinkInternalClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(pageDTO);
    }

}
