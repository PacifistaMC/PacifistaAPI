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
import fr.pacifista.api.web.news.service.entities.comments.PacifistaNewsComment;
import fr.pacifista.api.web.news.service.entities.news.PacifistaNews;
import fr.pacifista.api.web.news.service.mappers.news.PacifistaNewsMapper;
import fr.pacifista.api.web.news.service.repositories.comments.PacifistaNewsCommentLikeRepository;
import fr.pacifista.api.web.news.service.repositories.comments.PacifistaNewsCommentRepository;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsRepository;
import fr.pacifista.api.web.news.service.services.ban.PacifistaNewsBanCrudService;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkInternalClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
import org.jetbrains.annotations.Nullable;
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

    @MockitoBean
    private UserAuthClient authClient;

    @MockitoBean
    private PacifistaWebUserLinkInternalClient pacifistaLinkClient;

    @Autowired
    private PacifistaNewsBanCrudService banCrudService;

    @BeforeEach
    void resetDb() {
        likeRepository.deleteAll();
        commentRepository.deleteAll();
        newsRepository.deleteAll();
        banCrudService.getRepository().deleteAll();
    }

    @Test
    void testCreateCommentFailWhenNoUser() throws Exception {
        this.mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonHelper.toJson(new PacifistaNewsCommentDTO("dddsfsqdfqsdfqsdf", new PacifistaNewsDTO())))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    void testCreateCommentFailWhenNoMinecraftAccount() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        this.createComment(new PacifistaNewsCommentDTO("ddsqdfsqdfsqdfsqdfqsdf", null), true);
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

        this.createComment(new PacifistaNewsCommentDTO("dsqdfsqdfqsdfqsdfqsdfqsdfd", null), true);
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

    @Test
    void testCreateCommentOnNewsWithNoAccessOnDraft() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        PacifistaNewsDTO newsDTO = createNews(true);
        this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), true);

        newsDTO = createNews(false);
        this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
    }

    @Test
    void testCreateCommentWhenUserFunixProdIsBannedShouldFail() throws Exception {
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

        this.createComment(new PacifistaNewsCommentDTO(comment, newsDTO), false);

        this.addBan(new PacifistaNewsBanDTO(userDTO.getId(), UUID.randomUUID().toString()), userDTO);

        this.createComment(new PacifistaNewsCommentDTO(comment, newsDTO), true);
    }

    @Test
    void testCreateCommentWhenUserMinecraftIsBannedShouldFail() throws Exception {
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

        this.createComment(new PacifistaNewsCommentDTO(comment, newsDTO), false);

        this.addBan(new PacifistaNewsBanDTO(UUID.randomUUID(), linkDTO.getMinecraftUsername()), userDTO);

        this.createComment(new PacifistaNewsCommentDTO(comment, newsDTO), true);
    }

    @Test
    void testLikeCommentSuccess() throws Exception {
        UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final String comment = "dd" + UUID.randomUUID();
        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO createdComment = this.createComment(new PacifistaNewsCommentDTO(comment, newsDTO), false);
        assertNotNull(createdComment);

        this.likeComment(createdComment.getId().toString(), false);

        PageDTO<PacifistaNewsCommentLikeDTO> likes = getLikesOnComment(createdComment.getId().toString(), 0, false);
        assertEquals(1, likes.getContent().size());

        PacifistaNewsComment commentEnt = this.commentRepository.findByUuid(createdComment.getId().toString()).orElse(null);
        assertNotNull(commentEnt);
        assertEquals(1, commentEnt.getLikes());

        this.likeComment(createdComment.getId().toString(), true);

        userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        this.likeComment(createdComment.getId().toString(), false);

        likes = getLikesOnComment(createdComment.getId().toString(), 0, false);
        assertEquals(2, likes.getContent().size());
        likes = getLikesOnComment(createdComment.getId().toString(), 0, true);
        assertEquals(2, likes.getContent().size());

        commentEnt = this.commentRepository.findByUuid(createdComment.getId().toString()).orElse(null);
        assertNotNull(commentEnt);
        assertEquals(2, commentEnt.getLikes());
    }

    @Test
    void testLikeCommentFailWhenNewsIsDraftAndDoesNotHaveAccess() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(true);

        this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), true);
    }

    @Test
    void testLikeTwiceCommentShouldFail() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO createdComment = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(createdComment);

        this.likeComment(createdComment.getId().toString(), false);
        this.likeComment(createdComment.getId().toString(), true);
    }

    @Test
    void testRemoveLikeSuccess() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO createdComment = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(createdComment);

        this.likeComment(createdComment.getId().toString(), false);

        PageDTO<PacifistaNewsCommentLikeDTO> likes = getLikesOnComment(createdComment.getId().toString(), 0, false);
        assertEquals(1, likes.getContent().size());

        this.removeLike(createdComment.getId().toString(), false);

        likes = getLikesOnComment(createdComment.getId().toString(), 0, false);
        assertEquals(0, likes.getContent().size());

        this.removeLike(createdComment.getId().toString(), true);
    }

    @Test
    void testRemoveLikeFailWhenNoLike() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO createdComment = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(createdComment);

        this.removeLike(createdComment.getId().toString(), true);
    }

    @Test
    void testUpdateComment() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final String initialComment = "dd" + UUID.randomUUID();
        final PacifistaNewsCommentDTO createdComment = this.createComment(new PacifistaNewsCommentDTO(initialComment + UUID.randomUUID(), newsDTO), false);
        assertNotNull(createdComment);

        final String updatedComment = "ddUpdated" + UUID.randomUUID();

        final PacifistaNewsCommentDTO updated = this.updateComment(createdComment.getId().toString(), updatedComment, false);
        assertNotNull(updated);
        assertEquals(updatedComment, updated.getContent());
    }

    @Test
    void testUpdateCommentFailWhenNotOwner() throws Exception {
        UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final String initialComment = "dd" + UUID.randomUUID();
        final PacifistaNewsCommentDTO createdComment = this.createComment(new PacifistaNewsCommentDTO(initialComment + UUID.randomUUID(), newsDTO), false);
        assertNotNull(createdComment);

        final String updatedComment = "ddUpdated" + UUID.randomUUID();

        this.updateComment(createdComment.getId().toString(), updatedComment, false);

        userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        this.updateComment(createdComment.getId().toString(), updatedComment, true);
    }

    @Test
    void testUpdateCommentWhenNewsIsDraftAndDoesNotHaveAccessShouldFail() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO commentDTO = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(commentDTO);

        final PacifistaNews news = this.newsRepository.findByUuid(newsDTO.getId().toString()).orElse(null);
        assertNotNull(news);
        news.setDraft(true);
        this.newsRepository.save(news);

        this.updateComment(commentDTO.getId().toString(), "ddUpdated" + UUID.randomUUID(), true);
    }

    @Test
    void testUpdateCommentFailWhenNoComment() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);
        this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        this.updateComment(UUID.randomUUID().toString(), "ddUpdated" + UUID.randomUUID(), true);
    }

    @Test
    void testDeleteParentCommentAndCheckIfChildDeleted() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO parentComment = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(parentComment);

        final PacifistaNewsCommentDTO childComment = this.createComment(new PacifistaNewsCommentDTO(parentComment, "dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(childComment);

        PacifistaNews news = this.newsRepository.findByUuid(newsDTO.getId().toString()).orElse(null);
        assertNotNull(news);
        assertEquals(2, news.getComments());

        this.deleteComment(parentComment.getId().toString(), false);

        assertNull(
                this.commentRepository.findByUuid(childComment.getId().toString()).orElse(null)
        );

        news = this.newsRepository.findByUuid(newsDTO.getId().toString()).orElse(null);
        assertNotNull(news);
        assertEquals(0, news.getComments());

        this.commentRepository.findByUuid(childComment.getId().toString()).ifPresent(comment -> fail("Child comment should have been deleted"));
        this.commentRepository.findByUuid(parentComment.getId().toString()).ifPresent(comment -> fail("Parent comment should have been deleted"));
    }

    @Test
    void testCommentReplies() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO parentComment = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(parentComment);
        this.createComment(new PacifistaNewsCommentDTO(parentComment, "dd" + UUID.randomUUID(), newsDTO), false);

        PacifistaNewsComment comment = this.commentRepository.findByUuid(parentComment.getId().toString()).orElse(null);
        assertNotNull(comment);
        assertEquals(1, comment.getReplies());

        PacifistaNewsCommentDTO childComment = this.createComment(new PacifistaNewsCommentDTO(parentComment, "dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(childComment);

        comment = this.commentRepository.findByUuid(parentComment.getId().toString()).orElse(null);
        assertNotNull(comment);
        assertEquals(2, comment.getReplies());

        this.deleteComment(childComment.getId().toString(), false);

        comment = this.commentRepository.findByUuid(parentComment.getId().toString()).orElse(null);
        assertNotNull(comment);
        assertEquals(1, comment.getReplies());
    }

    @Test
    void testUpdateCommentFailWhenCommentTooShort() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO commentDTO = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(commentDTO);

        this.updateComment(commentDTO.getId().toString(), "d", true);
    }

    @Test
    void testCreateCommentFailWithCommentTooShort() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);
        this.createComment(new PacifistaNewsCommentDTO("d", newsDTO), true);
    }

    @Test
    void testDeleteSelfComment() throws Exception {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        final PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO commentDTO = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(commentDTO);

        PacifistaNews news = this.newsRepository.findByUuid(newsDTO.getId().toString()).orElse(null);
        assertNotNull(news);
        assertEquals(1, news.getComments());

        this.deleteComment(commentDTO.getId().toString(), false);

        news = this.newsRepository.findByUuid(newsDTO.getId().toString()).orElse(null);
        assertNotNull(news);
        assertEquals(0, news.getComments());
    }

    @Test
    void testDeleteOtherCommentFailWhenNoStaff() throws Exception {
        UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO commentDTO = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(commentDTO);

        userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        this.deleteComment(commentDTO.getId().toString(), true);
    }

    @Test
    void testDeleteOtherCommentWhenStaffModerator() throws Exception {
        UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO commentDTO = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(commentDTO);

        userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.PACIFISTA_MODERATOR);
        setUserMock(userDTO);

        linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        this.deleteComment(commentDTO.getId().toString(), false);
    }

    @Test
    void testDeleteOtherCommentWhenStaffAdmin() throws Exception {
        UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO commentDTO = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(commentDTO);

        userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.PACIFISTA_ADMIN);
        setUserMock(userDTO);

        linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        this.deleteComment(commentDTO.getId().toString(), false);
    }

    @Test
    void testDeleteOtherCommentWhenAdminGlobal() throws Exception {
        UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO commentDTO = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(commentDTO);

        userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.ADMIN);
        setUserMock(userDTO);

        linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        this.deleteComment(commentDTO.getId().toString(), false);
    }

    @Test
    void testGetCommentsAndAssertThatWeOnlyHaveParents() throws Exception {
        UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO commentDTO = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(commentDTO);

        this.createComment(new PacifistaNewsCommentDTO(commentDTO, "ddReply" + UUID.randomUUID(), newsDTO), false);

        final PageDTO<PacifistaNewsCommentDTO> comments = getCommentsOnNews(newsDTO.getId(), 0, false);
        assertEquals(1, comments.getContent().size());
    }

    @Test
    void testGetReplies() throws Exception {
        UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO commentDTO = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(commentDTO);

        final PacifistaNewsCommentDTO comment2 = this.createComment(new PacifistaNewsCommentDTO("ddReply" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(comment2);

        this.createComment(new PacifistaNewsCommentDTO(commentDTO, "ddReply" + UUID.randomUUID(), newsDTO), false);
        this.createComment(new PacifistaNewsCommentDTO(commentDTO, "ddReply" + UUID.randomUUID(), newsDTO), false);

        this.createComment(new PacifistaNewsCommentDTO(comment2, "ddReply" + UUID.randomUUID(), newsDTO), false);

        PageDTO<PacifistaNewsCommentDTO> replies = getCommentsRepliesOnNews(commentDTO.getId(), 0, false);
        assertEquals(2, replies.getContent().size());

        replies = getCommentsRepliesOnNews(comment2.getId(), 0, false);
        assertEquals(1, replies.getContent().size());
    }

    @Test
    void testGetCommentsByUser() throws Exception {
        UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);
        this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        final PacifistaNewsDTO newsDTO2 = createNews(false);
        this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO2), false);
        this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO2), false);

        PageDTO<PacifistaNewsCommentDTO> comments = getCommentsByUser(linkDTO.getMinecraftUsername(), 0, false);
        assertEquals(4, comments.getContent().size());
    }

    @Test
    void testCreateReplyOnReplyCommentShouldFail() throws Exception {
        UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO commentDTO = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(commentDTO);

        final PacifistaNewsCommentDTO reply = this.createComment(new PacifistaNewsCommentDTO(commentDTO, "ddReply" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(reply);

        this.createComment(new PacifistaNewsCommentDTO(reply, "ddReply" + UUID.randomUUID(), newsDTO), true);
    }

    @Test
    void testLikeStatusOnComment() throws Exception {
        UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        PacifistaWebUserLinkDTO linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        final PacifistaNewsDTO newsDTO = createNews(false);

        final PacifistaNewsCommentDTO commentDTO = this.createComment(new PacifistaNewsCommentDTO("dd" + UUID.randomUUID(), newsDTO), false);
        assertNotNull(commentDTO);

        PacifistaNewsCommentDTO gotComment = this.getCommentByNewsIdAndCommentId(newsDTO.getId(), commentDTO.getId(), true);
        assertNotNull(gotComment);
        assertFalse(gotComment.getLiked());

        gotComment = this.getCommentByNewsIdAndCommentId(newsDTO.getId(), commentDTO.getId(), false);
        assertNotNull(gotComment);
        assertFalse(gotComment.getLiked());

        this.likeComment(commentDTO.getId().toString(), false);

        gotComment = this.getCommentByNewsIdAndCommentId(newsDTO.getId(), commentDTO.getId(), true);
        assertNotNull(gotComment);
        assertTrue(gotComment.getLiked());

        gotComment = this.getCommentByNewsIdAndCommentId(newsDTO.getId(), commentDTO.getId(), false);
        assertNotNull(gotComment);
        assertFalse(gotComment.getLiked());

        userDTO = UserDTO.generateFakeDataForTestingPurposes();
        setUserMock(userDTO);

        linkDTO = new PacifistaWebUserLinkDTO(userDTO.getId(), UUID.randomUUID());
        linkDTO.setCreatedAt(new Date());
        linkDTO.setId(UUID.randomUUID());
        linkDTO.setMinecraftUsername("McUser" + UUID.randomUUID());
        linkDTO.setLinked(true);
        setPacifistaLinkMock(linkDTO);

        gotComment = this.getCommentByNewsIdAndCommentId(newsDTO.getId(), commentDTO.getId(), true);
        assertNotNull(gotComment);
        assertFalse(gotComment.getLiked());

        gotComment = this.getCommentByNewsIdAndCommentId(newsDTO.getId(), commentDTO.getId(), false);
        assertNotNull(gotComment);
        assertFalse(gotComment.getLiked());
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

    private void addBan(final PacifistaNewsBanDTO banDTO, final UserDTO userDTO) throws Exception {
        final UserRole role = userDTO.getRole();

        userDTO.setRole(UserRole.ADMIN);
        setUserMock(userDTO);

        this.mockMvc.perform(post("/web/news/bans")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonHelper.toJson(banDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        userDTO.setRole(role);
        setUserMock(userDTO);
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
            final MvcResult result = this.mockMvc.perform(patch(BASE_URL + "/" + commentId)
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

    @Nullable
    private PacifistaNewsCommentDTO getCommentByNewsIdAndCommentId(final UUID newsId, final UUID commentId, final boolean authed) throws Exception {
        final Iterable<PacifistaNewsCommentDTO> comments = this.getCommentsOnNews(newsId, 0, authed).getContent();

        for (final PacifistaNewsCommentDTO comment : comments) {
            if (comment.getId().equals(commentId)) {
                return comment;
            }
        }

        return null;
    }
}
