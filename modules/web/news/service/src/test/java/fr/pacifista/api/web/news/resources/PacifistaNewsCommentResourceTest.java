package fr.pacifista.api.web.news.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import com.google.gson.reflect.TypeToken;
import fr.pacifista.api.web.news.client.dtos.ban.PacifistaNewsBanDTO;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentDTO;
import fr.pacifista.api.web.news.client.dtos.comments.PacifistaNewsCommentLikeDTO;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.service.PacifistaWebNewsApp;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import fr.pacifista.api.web.news.service.mappers.news.PacifistaNewsMapper;
import fr.pacifista.api.web.news.service.repositories.comments.PacifistaNewsCommentLikeRepository;
import fr.pacifista.api.web.news.service.repositories.comments.PacifistaNewsCommentRepository;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsRepository;
import fr.pacifista.api.web.news.service.services.ban.PacifistaNewsBanCrudService;
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
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PacifistaWebNewsApp.class)
@AutoConfigureMockMvc
class PacifistaNewsCommentResourceTest {

    private final static String BASE_URL = "/web/news/comments";

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PacifistaNewsCommentRepository commentRepository;

    @Autowired
    private PacifistaNewsCommentLikeRepository likeRepository;

    @Autowired
    private PacifistaNewsRepository newsRepository;

    @Autowired
    private PacifistaNewsMapper newsMapper;

    @MockBean
    private UserAuthClient authClient;

    @MockBean
    private PacifistaWebUserLinkInternalClient pacifistaLinkClient;

    @MockBean
    private PacifistaNewsBanCrudService banCrudService;

    private List<PacifistaNewsBanDTO> bans = new ArrayList<>();

    @BeforeEach
    void resetDb() {
        likeRepository.deleteAll();
        commentRepository.deleteAll();
        newsRepository.deleteAll();
        reset(banCrudService);
        bans.clear();
    }

    @Test
    void testCreateCommentFailWhenNoUser() throws Exception {
        this.createComment(new PacifistaNewsCommentDTO("dd", new PacifistaNewsDTO()), true);
    }

    @Test
    void testCreateCommentFailWhenNoMinecraftAccount() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        this.createComment(new PacifistaNewsCommentDTO("dd", null), true);
    }

    @Test
    void testCreateCommentFailWhenMinecraftAccountNotLinked() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(false);
        setPacifistaLinkMock(linkDTO);

        this.createComment(new PacifistaNewsCommentDTO("dd", null), true);
    }

    @Test
    void testCreateCommentSuccess() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final String comment = "dd" + UUID.randomUUID();
        final PacifistaNewsDTO newsDTO = createNews(false);

        assertEquals(0, newsDTO.getComments());

        final PacifistaNewsCommentDTO createdComment = this.createComment(new PacifistaNewsCommentDTO(comment, newsDTO), false);

        assertNotNull(createdComment);
        assertNull(createdComment.getParent());
        assertEquals(comment, createdComment.getContent());
        assertEquals(0, createdComment.getLikes());
        assertNotNull(createdComment.getNews());
        assertEquals(userDTO.getId(), createdComment.getFunixProdUserId());
        assertEquals(linkDTO.getMinecraftUsername(), createdComment.getMinecraftUsername());

        final PacifistaNewsDTO updatedNews = getNews(newsDTO.getId());
        assertEquals(1, updatedNews.getComments());
    }

    @Test
    void testCreateCommentOnNewsDraftNoAccessUser() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final String comment = "dd" + UUID.randomUUID();
        final PacifistaNewsDTO newsDTO = createNews(true);

        this.createComment(new PacifistaNewsCommentDTO(comment, newsDTO), true);

        userDTO.setRole(UserRole.ADMIN);
        setUserMock(userDTO);

        this.createComment(new PacifistaNewsCommentDTO(comment, newsDTO), false);
    }

    @Test
    void testCreateCommentReplyFailWithParentUnknown() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final String comment = "dd" + UUID.randomUUID();
        final PacifistaNewsDTO newsDTO = createNews(false);
        final PacifistaNewsCommentDTO fakeParent = new PacifistaNewsCommentDTO("dd", newsDTO);
        fakeParent.setId(UUID.randomUUID());

        this.createComment(new PacifistaNewsCommentDTO(fakeParent, comment, newsDTO), true);
    }

    @Test
    void testCreateCommentReplySuccess() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final String comment = "dd" + UUID.randomUUID();
        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO parentComment = this.createComment(new PacifistaNewsCommentDTO(comment, newsDTO), false);
        assertNotNull(parentComment);

        PacifistaNewsDTO updatedNews = getNews(newsDTO.getId());
        assertEquals(1, updatedNews.getComments());

        final String reply = "ddReply" + UUID.randomUUID();
        final PacifistaNewsCommentDTO replyComment = this.createComment(new PacifistaNewsCommentDTO(parentComment, reply, newsDTO), false);
        assertNotNull(replyComment);

        updatedNews = getNews(newsDTO.getId());
        assertEquals(2, updatedNews.getComments());

        assertNotNull(replyComment.getParent());
        assertEquals(parentComment.getId(), replyComment.getParent().getId());
        assertEquals(reply, replyComment.getContent());
        assertEquals(0, replyComment.getLikes());
        assertEquals(newsDTO.getId(), replyComment.getNews().getId());
        assertEquals(userDTO.getId(), replyComment.getFunixProdUserId());
        assertEquals(linkDTO.getMinecraftUsername(), replyComment.getMinecraftUsername());
    }

    private void setUserMock(final UserDTO userDTO) {
        when(authClient.current(anyString())).thenReturn(userDTO);
    }

    private void setPacifistaLinkMock(final PacifistaWebUserLinkDTO linkDTO) {
        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(linkDTO), 1, 0, 0L, 0
        ));
    }

    private PacifistaNewsDTO createNews(final boolean draft) {
        final PacifistaNews news = new PacifistaNews();

        news.setOriginalWriter("FunixG" + UUID.randomUUID());
        news.setName(UUID.randomUUID().toString());
        news.setTitle(UUID.randomUUID().toString());
        news.setSubtitle(UUID.randomUUID().toString());
        news.setBodyHtml(UUID.randomUUID().toString());
        news.setBodyMarkdown(UUID.randomUUID().toString());
        news.setDraft(draft);
        news.setLikes(0);
        news.setComments(0);
        news.setViews(0);

        return newsMapper.toDto(newsRepository.save(news));
    }

    private PacifistaNewsDTO getNews(final UUID newsId) throws Exception {
        final Type type = new TypeToken<PacifistaNewsDTO>() {}.getType();
        final MvcResult result = mockMvc.perform(get("/web/news/" + newsId)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), type);

    }

    private void addBan(final PacifistaNewsBanDTO banDTO) {
        bans.add(banDTO);
        when(banCrudService.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                bans, 1, 0, (long) bans.size(), bans.size()
        ));
    }

    private PageDTO<PacifistaNewsCommentDTO> getCommentsOnNews(final UUID newsId, final int page, final boolean authed) throws Exception {
        final Type type = new TypeToken<PageDTO<PacifistaNewsCommentDTO>>() {}.getType();
        final MvcResult result;

        if (authed) {
            result = mockMvc.perform(get(BASE_URL)
                    .param("newsId", newsId.toString())
                    .param("page", String.valueOf(page))
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
            ).andExpect(status().isOk()).andReturn();
        } else {
            result = mockMvc.perform(get(BASE_URL)
                    .param("newsId", newsId.toString())
                    .param("page", String.valueOf(page))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();
        }

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
    }

    private PageDTO<PacifistaNewsCommentDTO> getCommentsRepliesOnNews(final UUID commentId, final int page, final boolean authed) throws Exception {
        final Type type = new TypeToken<PageDTO<PacifistaNewsCommentDTO>>() {}.getType();
        final MvcResult result;

        if (authed) {
            result = mockMvc.perform(get(BASE_URL + "/replies")
                    .param("commentId", commentId.toString())
                    .param("page", String.valueOf(page))
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
            ).andExpect(status().isOk()).andReturn();
        } else {
            result = mockMvc.perform(get(BASE_URL + "/replies")
                    .param("commentId", commentId.toString())
                    .param("page", String.valueOf(page))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();
        }

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
    }

    private PageDTO<PacifistaNewsCommentDTO> getCommentsByUser(final String minecraftUsername, final int page, final boolean authed) throws Exception {
        final Type type = new TypeToken<PageDTO<PacifistaNewsCommentDTO>>() {}.getType();
        final MvcResult result;

        if (authed) {
            result = mockMvc.perform(get(BASE_URL + "/user")
                    .param("minecraftUsername", minecraftUsername)
                    .param("page", String.valueOf(page))
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
            ).andExpect(status().isOk()).andReturn();
        } else {
            result = mockMvc.perform(get(BASE_URL + "/user")
                    .param("minecraftUsername", minecraftUsername)
                    .param("page", String.valueOf(page))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();
        }

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
    }

    private PacifistaNewsCommentDTO createComment(final PacifistaNewsCommentDTO commentDTO, final boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(post(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonHelper.toJson(commentDTO))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().is4xxClientError());
            return null;
        } else {
            final MvcResult result = this.mockMvc.perform(post(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonHelper.toJson(commentDTO))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();

            return jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaNewsCommentDTO.class);
        }
    }

    private PacifistaNewsCommentDTO updateComment(final String commentId, final String comment, final boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(patch(BASE_URL + "/" + commentId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(comment)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().is4xxClientError());
            return null;
        } else {
            final MvcResult result = this.mockMvc.perform(post(BASE_URL + "/" + commentId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(comment)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();

            return jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaNewsCommentDTO.class);
        }
    }

    private void deleteComment(final String commentId, final boolean needToFail) throws Exception {
        this.mockMvc.perform(delete(BASE_URL + "/" + commentId)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized());

        if (needToFail) {
            this.mockMvc.perform(delete(BASE_URL + "/" + commentId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().is4xxClientError());
        } else {
            this.mockMvc.perform(delete(BASE_URL + "/" + commentId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

            this.mockMvc.perform(delete(BASE_URL + "/" + commentId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound());
        }
    }

    private PageDTO<PacifistaNewsCommentLikeDTO> getLikesOnComment(final String commentId, final int page, final boolean authed) throws Exception {
        final Type type = new TypeToken<PageDTO<PacifistaNewsCommentLikeDTO>>() {}.getType();
        final MvcResult result;

        if (authed) {
            result = mockMvc.perform(get(BASE_URL + "/likes")
                    .param("commentId", commentId)
                    .param("page", String.valueOf(page))
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
            ).andExpect(status().isOk()).andReturn();
        } else {
            result = mockMvc.perform(get(BASE_URL + "/likes")
                    .param("commentId", commentId)
                    .param("page", String.valueOf(page))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();
        }

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), type);
    }

    private PacifistaNewsCommentLikeDTO likeComment(final String commentId, final boolean needToFail) throws Exception {
        this.mockMvc.perform(post(BASE_URL + "/like/" + commentId)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized());

        if (needToFail) {
            this.mockMvc.perform(post(BASE_URL + "/like/" + commentId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().is4xxClientError());
            return null;
        } else {
            final MvcResult result = this.mockMvc.perform(post(BASE_URL + "/like/" + commentId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();

            return jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaNewsCommentLikeDTO.class);
        }
    }

    private void removeLike(final String commentId, final boolean needToFail) throws Exception {
        this.mockMvc.perform(delete(BASE_URL + "/like/" + commentId)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized());

        if (needToFail) {
            this.mockMvc.perform(delete(BASE_URL + "/like/" + commentId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().is4xxClientError());
        } else {
            this.mockMvc.perform(delete(BASE_URL + "/like/" + commentId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

            this.mockMvc.perform(delete(BASE_URL + "/like/" + commentId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound());
        }
    }
}
