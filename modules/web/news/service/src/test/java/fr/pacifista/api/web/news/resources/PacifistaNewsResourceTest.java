package fr.pacifista.api.web.news.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import com.google.gson.reflect.TypeToken;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsLikeDTO;
import fr.pacifista.api.web.news.service.PacifistaWebNewsApp;
import fr.pacifista.api.web.news.service.repositories.comments.PacifistaNewsCommentLikeRepository;
import fr.pacifista.api.web.news.service.repositories.comments.PacifistaNewsCommentRepository;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsImageRepository;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsLikeRepository;
import fr.pacifista.api.web.news.service.repositories.news.PacifistaNewsRepository;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PacifistaWebNewsApp.class)
@AutoConfigureMockMvc
class PacifistaNewsResourceTest {

    private static final String BASE_ROUTE = "/web/news";
    private static final String LIKE_ROUTE = BASE_ROUTE + "/like";
    private static final String LIKES_ROUTE = BASE_ROUTE + "/likes";

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PacifistaNewsRepository newsRepository;

    @Autowired
    private PacifistaNewsImageRepository imageRepository;

    @Autowired
    private PacifistaNewsLikeRepository likeRepository;

    @Autowired
    private PacifistaNewsCommentRepository commentRepository;

    @Autowired
    private PacifistaNewsCommentLikeRepository commentLikeRepository;

    @MockBean
    private UserAuthClient authClient;

    @MockBean
    private PacifistaWebUserLinkInternalClient pacifistaLinkClient;

    @BeforeEach
    void resetDatabase() {
        this.imageRepository.deleteAll();
        this.likeRepository.deleteAll();
        this.commentLikeRepository.deleteAll();
        this.commentRepository.deleteAll();
        this.newsRepository.deleteAll();
    }

    @Test
    void testAccessSecurityForCreationFailOfNoAccess() throws Exception {
        final UserDTO mockUser = UserDTO.generateFakeDataForTestingPurposes();
        when(authClient.current(anyString())).thenReturn(mockUser);
        final PacifistaNewsDTO createNewsRequest = this.createNewsDTORequest();

        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(), 1, 0, 0L, 0
        ));

        this.sendCreateRequest(createNewsRequest, true);

        final PacifistaWebUserLinkDTO mockUserMinecraftLink = new PacifistaWebUserLinkDTO(mockUser.getId(), UUID.randomUUID());
        mockUserMinecraftLink.setMinecraftUsername("mockUser" + UUID.randomUUID());
        mockUserMinecraftLink.setLinked(false);
        mockUserMinecraftLink.setCreatedAt(new Date());
        mockUserMinecraftLink.setId(UUID.randomUUID());

        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(mockUserMinecraftLink), 1, 0, 1L, 1
        ));
        this.sendCreateRequest(createNewsRequest, true);

        mockUser.setRole(UserRole.ADMIN);
        when(authClient.current(anyString())).thenReturn(mockUser);
        this.sendCreateRequest(createNewsRequest, true);
    }

    @Test
    void testCreateNewsSuccess() throws Exception {
        final UserDTO mockUser = UserDTO.generateFakeDataForTestingPurposes();
        mockUser.setRole(UserRole.ADMIN);
        when(authClient.current(anyString())).thenReturn(mockUser);
        final PacifistaNewsDTO createNewsRequest = this.createNewsDTORequest();

        final PacifistaWebUserLinkDTO mockUserMinecraftLink = new PacifistaWebUserLinkDTO(mockUser.getId(), UUID.randomUUID());
        mockUserMinecraftLink.setMinecraftUsername("mockUser" + UUID.randomUUID());
        mockUserMinecraftLink.setLinked(true);
        mockUserMinecraftLink.setCreatedAt(new Date());
        mockUserMinecraftLink.setId(UUID.randomUUID());

        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(mockUserMinecraftLink), 1, 0, 1L, 1
        ));

        final PacifistaNewsDTO createdNews = this.sendCreateRequest(createNewsRequest, false);

        assertNotNull(createdNews);
        assertEquals(mockUserMinecraftLink.getMinecraftUsername(), createdNews.getOriginalWriter());
        assertNull(createdNews.getUpdateWriter());
        assertEquals(createNewsRequest.getName(), createdNews.getName());
        assertEquals(createNewsRequest.getTitle(), createdNews.getTitle());
        assertEquals(createNewsRequest.getSubtitle(), createdNews.getSubtitle());
        assertNotNull(createdNews.getArticleImageId());
        assertNotNull(createdNews.getArticleImageIdLowRes());
        assertEquals(createNewsRequest.getBodyHtml(), createdNews.getBodyHtml());
        assertEquals(createNewsRequest.getBodyMarkdown(), createdNews.getBodyMarkdown());
        assertEquals(createNewsRequest.getDraft(), createdNews.getDraft());
        assertEquals(0, createdNews.getLikes());
        assertEquals(0, createdNews.getComments());
        assertEquals(0, createdNews.getViews());

        this.getImageRequest(UUID.randomUUID(), true);
        this.getImageRequest(createdNews.getArticleImageId(), false);
        this.getImageRequest(createdNews.getArticleImageIdLowRes(), false);
    }

    @Test
    void testCreateDoubleNewsWithSameNameNeedFail() throws Exception {
        final UserDTO mockUser = UserDTO.generateFakeDataForTestingPurposes();
        mockUser.setRole(UserRole.ADMIN);
        when(authClient.current(anyString())).thenReturn(mockUser);
        final PacifistaNewsDTO createNewsRequest = this.createNewsDTORequest();

        final PacifistaWebUserLinkDTO mockUserMinecraftLink = new PacifistaWebUserLinkDTO(mockUser.getId(), UUID.randomUUID());
        mockUserMinecraftLink.setMinecraftUsername("mockUser" + UUID.randomUUID());
        mockUserMinecraftLink.setLinked(true);
        mockUserMinecraftLink.setCreatedAt(new Date());
        mockUserMinecraftLink.setId(UUID.randomUUID());

        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(mockUserMinecraftLink), 1, 0, 1L, 1
        ));

        final PacifistaNewsDTO createdNews = this.sendCreateRequest(createNewsRequest, false);
        assertNotNull(createdNews);
        final PacifistaNewsDTO createNewsRequest2 = this.createNewsDTORequest();
        createNewsRequest2.setName(createdNews.getName());

        this.sendCreateRequest(createNewsRequest2, true);
        createNewsRequest2.setName(createNewsRequest2.getName().toUpperCase());
        this.sendCreateRequest(createNewsRequest2, true);
        createNewsRequest2.setName(createNewsRequest2.getName().toLowerCase());
        this.sendCreateRequest(createNewsRequest2, true);
    }

    @Test
    void testUpdateNewsSuccess() throws Exception {
        final UserDTO mockUser = UserDTO.generateFakeDataForTestingPurposes();
        mockUser.setRole(UserRole.ADMIN);
        when(authClient.current(anyString())).thenReturn(mockUser);
        final PacifistaNewsDTO createNewsRequest = this.createNewsDTORequest();

        final PacifistaWebUserLinkDTO mockUserMinecraftLink = new PacifistaWebUserLinkDTO(mockUser.getId(), UUID.randomUUID());
        mockUserMinecraftLink.setMinecraftUsername("mockUsername" + UUID.randomUUID());
        mockUserMinecraftLink.setLinked(true);
        mockUserMinecraftLink.setCreatedAt(new Date());
        mockUserMinecraftLink.setId(UUID.randomUUID());

        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(mockUserMinecraftLink), 1, 0, 1L, 1
        ));

        final PacifistaNewsDTO createdNews = this.sendCreateRequest(createNewsRequest, false);
        assertNotNull(createdNews);
        createdNews.setTitle(createdNews.getTitle() + UUID.randomUUID() + "oh !");
        assertNull(createdNews.getUpdateWriter());

        final PacifistaNewsDTO updatedNews = this.updateRequest(createdNews, false);
        assertNotNull(updatedNews);
        assertEquals(createdNews.getTitle(), updatedNews.getTitle());
        assertEquals(updatedNews.getUpdateWriter(), mockUserMinecraftLink.getMinecraftUsername());
        assertEquals(createdNews.getArticleImageId(), updatedNews.getArticleImageId());
        assertEquals(createdNews.getArticleImageIdLowRes(), updatedNews.getArticleImageIdLowRes());

        this.getImageRequest(updatedNews.getArticleImageId(), false);
        this.getImageRequest(updatedNews.getArticleImageIdLowRes(), false);

        updatedNews.setTitle(createdNews.getTitle() + UUID.randomUUID() + "oh mais avec image !");
        final PacifistaNewsDTO updatedNewsWithImageChange = this.updateRequestWithImage(updatedNews, false);
        assertNotNull(updatedNewsWithImageChange);
        assertEquals(updatedNews.getTitle(), updatedNewsWithImageChange.getTitle());
        assertEquals(updatedNewsWithImageChange.getUpdateWriter(), mockUserMinecraftLink.getMinecraftUsername());
        assertNotEquals(updatedNews.getArticleImageId(), updatedNewsWithImageChange.getArticleImageId());
        assertNotEquals(updatedNews.getArticleImageIdLowRes(), updatedNewsWithImageChange.getArticleImageIdLowRes());

        this.getImageRequest(updatedNewsWithImageChange.getArticleImageId(), false);
        this.getImageRequest(updatedNewsWithImageChange.getArticleImageIdLowRes(), false);
    }

    @Test
    void testDeleteNews() throws Exception {
        final UserDTO mockUser = UserDTO.generateFakeDataForTestingPurposes();
        mockUser.setRole(UserRole.ADMIN);
        when(authClient.current(anyString())).thenReturn(mockUser);
        final PacifistaNewsDTO createNewsRequest = this.createNewsDTORequest();

        final PacifistaWebUserLinkDTO mockUserMinecraftLink = new PacifistaWebUserLinkDTO(mockUser.getId(), UUID.randomUUID());
        mockUserMinecraftLink.setMinecraftUsername("mockUser" + UUID.randomUUID());
        mockUserMinecraftLink.setLinked(true);
        mockUserMinecraftLink.setCreatedAt(new Date());
        mockUserMinecraftLink.setId(UUID.randomUUID());

        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(mockUserMinecraftLink), 1, 0, 1L, 1
        ));

        final PacifistaNewsDTO createdNews = this.sendCreateRequest(createNewsRequest, false);
        assertNotNull(createdNews);

        final PacifistaNewsLikeDTO like = this.addLikeOnNews(createdNews.getId(), false);
        assertNotNull(like);

        this.deleteNews(createdNews.getId(), false);
        this.deleteNews(UUID.randomUUID(), true);

        this.getImageRequest(createdNews.getArticleImageId(), true);
        this.getImageRequest(createdNews.getArticleImageIdLowRes(), true);

        assertTrue(this.likeRepository.findByUuid(like.getId().toString()).isEmpty());
    }

    @Test
    void testGetAllNews() throws Exception {
        PageDTO<PacifistaNewsDTO> news = this.getAll(0, false);
        assertEquals(0, news.getContent().size());

        final UserDTO mockUser = UserDTO.generateFakeDataForTestingPurposes();
        mockUser.setRole(UserRole.ADMIN);
        when(authClient.current(anyString())).thenReturn(mockUser);
        final PacifistaNewsDTO createNewsRequest = this.createNewsDTORequest();

        final PacifistaWebUserLinkDTO mockUserMinecraftLink = new PacifistaWebUserLinkDTO(mockUser.getId(), UUID.randomUUID());
        mockUserMinecraftLink.setMinecraftUsername("mockUser" + UUID.randomUUID());
        mockUserMinecraftLink.setLinked(true);
        mockUserMinecraftLink.setCreatedAt(new Date());
        mockUserMinecraftLink.setId(UUID.randomUUID());

        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(mockUserMinecraftLink), 1, 0, 1L, 1
        ));

        final PacifistaNewsDTO createdNews = this.sendCreateRequest(createNewsRequest, false);
        assertNotNull(createdNews);

        news = this.getAll(0, false);
        assertEquals(0, news.getContent().size());

        this.getNewsById(createdNews.getId(), true, false, true);
        final PacifistaNewsDTO gotNews = this.getNewsById(createdNews.getId(), false, true, true);
        assertNotNull(gotNews);
        assertEquals(createdNews.getId(), gotNews.getId());

        news = this.getAll(0, true);
        assertEquals(1, news.getContent().size());

        mockUser.setRole(UserRole.USER);
        when(authClient.current(anyString())).thenReturn(mockUser);
        news = this.getAll(0, true);
        assertEquals(0, news.getContent().size());
        this.getNewsById(createdNews.getId(), true, true, true);

        mockUser.setRole(UserRole.ADMIN);
        when(authClient.current(anyString())).thenReturn(mockUser);
        this.getNewsById(createdNews.getId(), false, true, true);

        createdNews.setDraft(false);
        this.updateRequest(createdNews, false);

        news = this.getAll(0, false);
        assertEquals(1, news.getContent().size());

        news = this.getAll(0, true);
        assertEquals(1, news.getContent().size());

        this.getNewsById(createdNews.getId(), false, true, true);
        this.getNewsById(createdNews.getId(), false, false, true);
    }

    @Test
    void testLikesOnNews() throws Exception {
        UserDTO mockUser = UserDTO.generateFakeDataForTestingPurposes();
        mockUser.setRole(UserRole.ADMIN);
        when(authClient.current(anyString())).thenReturn(mockUser);
        final PacifistaNewsDTO createNewsRequest = this.createNewsDTORequest();

        PacifistaWebUserLinkDTO mockUserMinecraftLink = new PacifistaWebUserLinkDTO(mockUser.getId(), UUID.randomUUID());
        mockUserMinecraftLink.setMinecraftUsername("mockUser" + UUID.randomUUID());
        mockUserMinecraftLink.setLinked(true);
        mockUserMinecraftLink.setCreatedAt(new Date());
        mockUserMinecraftLink.setId(UUID.randomUUID());

        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(mockUserMinecraftLink), 1, 0, 1L, 1
        ));

        final PacifistaNewsDTO createdNews = this.sendCreateRequest(createNewsRequest, false);
        assertNotNull(createdNews);

        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(), 1, 0, 0L, 0
        ));
        this.addLikeOnNews(createdNews.getId(), true);
        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(mockUserMinecraftLink), 1, 0, 1L, 1
        ));

        this.addLikeOnNews(createdNews.getId(), false);
        PageDTO<PacifistaNewsLikeDTO> likes = this.getLikes(createdNews.getId(), 0, false, true);
        assertEquals(1, likes.getContent().size());
        PacifistaNewsDTO tmp = this.getNewsById(createdNews.getId(), false, true, true);
        assertEquals(1, tmp.getLikes());

        this.addLikeOnNews(createdNews.getId(), true);
        this.removeLikeOnNews(createdNews.getId(), false);
        likes = this.getLikes(createdNews.getId(), 0, false, true);
        assertEquals(0, likes.getContent().size());
        tmp = this.getNewsById(createdNews.getId(), false, true, true);
        assertEquals(0, tmp.getLikes());

        this.removeLikeOnNews(createdNews.getId(), true);
        this.addLikeOnNews(createdNews.getId(), false);
        likes = this.getLikes(createdNews.getId(), 0, false, true);
        assertEquals(1, likes.getContent().size());
        tmp = this.getNewsById(createdNews.getId(), false, true, true);
        assertEquals(1, tmp.getLikes());

        this.removeLikeOnNews(createdNews.getId(), false);
        likes = this.getLikes(createdNews.getId(), 0, false, true);
        assertEquals(0, likes.getContent().size());
        tmp = this.getNewsById(createdNews.getId(), false, true, true);
        assertEquals(0, tmp.getLikes());

        this.getLikes(UUID.randomUUID(), 0, true, true);
        this.getLikes(UUID.randomUUID(), 0, true, false);

        mockUser.setRole(UserRole.USER);
        when(authClient.current(anyString())).thenReturn(mockUser);
        this.addLikeOnNews(createdNews.getId(), true);

        this.getLikes(createdNews.getId(), 0, true, true);
        mockUser.setRole(UserRole.ADMIN);
        when(authClient.current(anyString())).thenReturn(mockUser);
        this.getLikes(createdNews.getId(), 0, false, true);

        createdNews.setDraft(false);
        this.updateRequest(createdNews, false);

        mockUser.setRole(UserRole.USER);
        when(authClient.current(anyString())).thenReturn(mockUser);

        this.addLikeOnNews(createdNews.getId(), false);
        this.removeLikeOnNews(createdNews.getId(), false);
        likes = this.getLikes(createdNews.getId(), 0, false, true);
        assertEquals(0, likes.getContent().size());
        tmp = this.getNewsById(createdNews.getId(), false, true, true);
        assertEquals(0, tmp.getLikes());

        this.removeLikeOnNews(createdNews.getId(), true);
        this.addLikeOnNews(createdNews.getId(), false);
        likes = this.getLikes(createdNews.getId(), 0, false, true);
        assertEquals(1, likes.getContent().size());
        tmp = this.getNewsById(createdNews.getId(), false, true, true);
        assertEquals(1, tmp.getLikes());

        this.removeLikeOnNews(createdNews.getId(), false);
        likes = this.getLikes(createdNews.getId(), 0, false, true);
        assertEquals(0, likes.getContent().size());
        tmp = this.getNewsById(createdNews.getId(), false, true, true);
        assertEquals(0, tmp.getLikes());


        this.addLikeOnNews(createdNews.getId(), false);

        mockUser = UserDTO.generateFakeDataForTestingPurposes();
        mockUser.setRole(UserRole.ADMIN);
        when(authClient.current(anyString())).thenReturn(mockUser);

        mockUserMinecraftLink = new PacifistaWebUserLinkDTO(mockUser.getId(), UUID.randomUUID());
        mockUserMinecraftLink.setMinecraftUsername("mockUser" + UUID.randomUUID());
        mockUserMinecraftLink.setLinked(true);
        mockUserMinecraftLink.setCreatedAt(new Date());
        mockUserMinecraftLink.setId(UUID.randomUUID());

        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(mockUserMinecraftLink), 1, 0, 1L, 1
        ));

        this.addLikeOnNews(createdNews.getId(), false);

        likes = this.getLikes(createdNews.getId(), 0, false, false);
        assertEquals(2, likes.getContent().size());
        tmp = this.getNewsById(createdNews.getId(), false, true, true);
        assertEquals(2, tmp.getLikes());
    }

    @Test
    void testAddViews() throws Exception {
        UserDTO mockUser = UserDTO.generateFakeDataForTestingPurposes();
        mockUser.setRole(UserRole.ADMIN);
        when(authClient.current(anyString())).thenReturn(mockUser);
        final PacifistaNewsDTO createNewsRequest = this.createNewsDTORequest();
        createNewsRequest.setDraft(false);

        PacifistaWebUserLinkDTO mockUserMinecraftLink = new PacifistaWebUserLinkDTO(mockUser.getId(), UUID.randomUUID());
        mockUserMinecraftLink.setMinecraftUsername("mockUser" + UUID.randomUUID());
        mockUserMinecraftLink.setLinked(true);
        mockUserMinecraftLink.setCreatedAt(new Date());
        mockUserMinecraftLink.setId(UUID.randomUUID());

        when(pacifistaLinkClient.getAll(anyString(), anyString(), anyString(), anyString())).thenReturn(new PageDTO<>(
                List.of(mockUserMinecraftLink), 1, 0, 1L, 1
        ));

        final PacifistaNewsDTO createdNews = this.sendCreateRequest(createNewsRequest, false);
        assertNotNull(createdNews);

        PacifistaNewsDTO news = this.getNewsById(createdNews.getId(), false, false, true);
        assertNotNull(news);
        assertEquals(1, news.getViews());

        news = this.getNewsById(createdNews.getId(), false, false, true);
        assertNotNull(news);
        assertEquals(2, news.getViews());

        news = this.getNewsById(createdNews.getId(), false, false, false);
        assertNotNull(news);
        assertEquals(3, news.getViews());

        news = this.getNewsById(createdNews.getId(), false, false, false);
        assertNotNull(news);
        assertEquals(3, news.getViews());
    }

    private PacifistaNewsDTO createNewsDTORequest() {
        final PacifistaNewsDTO newsDTO = new PacifistaNewsDTO();

        newsDTO.setName("test-article-name-" + UUID.randomUUID());
        newsDTO.setTitle("Test Article Title " + UUID.randomUUID());
        newsDTO.setSubtitle("Test Article Sub title " + UUID.randomUUID());
        newsDTO.setBodyHtml("<h1>Test Body Html" + UUID.randomUUID() + "</h1>");
        newsDTO.setBodyMarkdown("# Test Body Html" + UUID.randomUUID());
        newsDTO.setDraft(true);
        return newsDTO;
    }

    private PacifistaNewsDTO sendCreateRequest(final PacifistaNewsDTO request, boolean needToFail) throws Exception {
        InputStream imageStream = getClass().getResourceAsStream("/test-image.jpeg");
        MockMultipartFile newsImage = new MockMultipartFile(
                "image",
                "test-image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                imageStream
        );

        final MockMultipartFile metadata = new MockMultipartFile(
                "dto",
                "dto",
                MediaType.APPLICATION_JSON_VALUE,
                jsonHelper.toJson(request).getBytes(StandardCharsets.UTF_8));

        if (needToFail) {
            this.mockMvc.perform(multipart(BASE_ROUTE)
                            .file(newsImage)
                            .file(metadata)
                            .accept(MediaType.APPLICATION_JSON)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    )
                    .andExpect(status().is4xxClientError());
            return null;
        }

        MvcResult result = this.mockMvc.perform(multipart(BASE_ROUTE)
                        .file(newsImage)
                        .file(metadata)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                )
                .andExpect(status().isOk())
                .andReturn();

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaNewsDTO.class);
    }

    private PacifistaNewsDTO updateRequest(final PacifistaNewsDTO request, boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(put(BASE_ROUTE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(this.jsonHelper.toJson(request))
                            .accept(MediaType.APPLICATION_JSON)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    )
                    .andExpect(status().is4xxClientError());
            return null;
        }

        MvcResult result = this.mockMvc.perform(put(BASE_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.jsonHelper.toJson(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                )
                .andExpect(status().isOk())
                .andReturn();

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaNewsDTO.class);
    }

    private PacifistaNewsDTO updateRequestWithImage(final PacifistaNewsDTO request, boolean needToFail) throws Exception {
        InputStream imageStream = getClass().getResourceAsStream("/test-image.jpeg");
        MockMultipartFile newsImage = new MockMultipartFile(
                "image",
                "test-image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                imageStream
        );

        final MockMultipartFile metadata = new MockMultipartFile(
                "dto",
                "dto",
                MediaType.APPLICATION_JSON_VALUE,
                jsonHelper.toJson(request).getBytes(StandardCharsets.UTF_8));

        final MvcResult result;

        if (needToFail) {
            this.mockMvc.perform(multipart(BASE_ROUTE + "/file")
                            .file(newsImage)
                            .file(metadata)
                            .accept(MediaType.APPLICATION_JSON)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    )
                    .andExpect(status().is4xxClientError());
            return null;
        } else {
            result = this.mockMvc.perform(multipart(BASE_ROUTE + "/file")
                            .file(newsImage)
                            .file(metadata)
                            .accept(MediaType.APPLICATION_JSON)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    )
                    .andExpect(status().isOk())
                    .andReturn();
        }

        return jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaNewsDTO.class);
    }

    private void deleteNews(final UUID newsId, boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(delete(BASE_ROUTE + "?id=" + newsId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().is4xxClientError());
            return;
        }

        this.mockMvc.perform(delete(BASE_ROUTE + "?id=" + newsId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        this.mockMvc.perform(delete(BASE_ROUTE + "?id=" + newsId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    private PageDTO<PacifistaNewsDTO> getAll(final int page, boolean authed) throws Exception {
        final Type gsonType = new TypeToken<PageDTO<PacifistaNewsDTO>>() {}.getType();
        final MvcResult result;

        if (authed) {
            result = this.mockMvc.perform(get(BASE_ROUTE)
                    .param("page", Integer.toString(page))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();
        } else {
            result = this.mockMvc.perform(get(BASE_ROUTE)
                    .param("page", Integer.toString(page))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();
        }

        return this.jsonHelper.fromJson(result.getResponse().getContentAsString(), gsonType);
    }

    private PacifistaNewsDTO getNewsById(final UUID newsId, boolean needToFail, boolean authed, boolean randomIp) throws Exception {
        final String ip;
        if (randomIp) {
            ip = this.generateRandomIpAddress();
        } else {
            ip = "10.0.0.1";
        }

        if (needToFail) {
            if (authed) {
                this.mockMvc.perform(get(BASE_ROUTE + "/" + newsId)
                        .remoteAddress(ip)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError());
            } else {
                this.mockMvc.perform(get(BASE_ROUTE + "/" + newsId)
                        .remoteAddress(ip)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError());
            }
            return null;
        }

        final MvcResult result;

        if (authed) {
            result = this.mockMvc.perform(get(BASE_ROUTE + "/" + newsId)
                    .remoteAddress(ip)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();
        } else {
            result = this.mockMvc.perform(get(BASE_ROUTE + "/" + newsId)
                    .remoteAddress(ip)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();
        }

        return this.jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaNewsDTO.class);
    }

    private void getImageRequest(final UUID imageId, boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(get(BASE_ROUTE + "/file/" + imageId)).andExpect(status().is4xxClientError());
        } else {
            this.mockMvc.perform(get(BASE_ROUTE + "/file/" + imageId)).andExpect(status().isOk());
        }
    }

    private PageDTO<PacifistaNewsLikeDTO> getLikes(final UUID newsId, final int page, boolean needToFail, boolean authed) throws Exception {
        if (needToFail) {
            if (authed) {
                this.mockMvc.perform(get(LIKES_ROUTE + "/" + newsId)
                        .param("page", Integer.toString(page))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError());
            } else {
                this.mockMvc.perform(get(LIKES_ROUTE + "/" + newsId)
                        .param("page", Integer.toString(page))
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError());
            }

            return null;
        }

        final Type gsonType = new TypeToken<PageDTO<PacifistaNewsLikeDTO>>() {}.getType();
        final MvcResult result;

        if (authed) {
            result = this.mockMvc.perform(get(LIKES_ROUTE + "/" + newsId)
                    .param("page", Integer.toString(page))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();
        } else {
            result = this.mockMvc.perform(get(LIKES_ROUTE + "/" + newsId)
                    .param("page", Integer.toString(page))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk()).andReturn();
        }

        return this.jsonHelper.fromJson(result.getResponse().getContentAsString(), gsonType);
    }

    private PacifistaNewsLikeDTO addLikeOnNews(final UUID newsId, boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(post(LIKE_ROUTE + "/" + newsId)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    ).andExpect(status().is4xxClientError());
            return null;
        }

        MvcResult result = this.mockMvc.perform(post(LIKE_ROUTE + "/" + newsId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
        ).andExpect(status().isOk()).andReturn();

        return this.jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaNewsLikeDTO.class);
    }

    private void removeLikeOnNews(final UUID newsId, boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(delete(LIKE_ROUTE + "/" + newsId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
            ).andExpect(status().is4xxClientError());
        } else {
            this.mockMvc.perform(delete(LIKE_ROUTE + "/" + newsId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
            ).andExpect(status().isOk());
        }
    }

    protected static String generateRandomIpAddress() {
        final Random random = new Random();

        return String.format(
                "%d.%d.%d.%d",
                random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255),
                random.nextInt(255)
        );
    }

}
