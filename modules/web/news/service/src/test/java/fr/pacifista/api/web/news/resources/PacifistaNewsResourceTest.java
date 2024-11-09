package fr.pacifista.api.web.news.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.test.beans.JsonHelper;
import com.google.gson.reflect.TypeToken;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsDTO;
import fr.pacifista.api.web.news.client.dtos.news.PacifistaNewsLikeDTO;
import fr.pacifista.api.web.user.client.clients.PacifistaWebUserLinkInternalClient;
import fr.pacifista.api.web.user.client.dtos.PacifistaWebUserLinkDTO;
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

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PacifistaNewsResourceTest {

    private static final String BASE_ROUTE = "/web/news";
    private static final String LIKE_ROUTE = BASE_ROUTE + "/like";
    private static final String LIKES_ROUTE = BASE_ROUTE + "/likes";

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthClient authClient;

    @MockBean
    private PacifistaWebUserLinkInternalClient pacifistaLinkClient;

    @Test
    void testCreationNews() {

    }

    private PacifistaNewsDTO createNewsDTORequest(final PacifistaWebUserLinkDTO pacifistaWebUserLinkDTO) {
        final PacifistaNewsDTO newsDTO = new PacifistaNewsDTO();

        newsDTO.setOriginalWriter(pacifistaWebUserLinkDTO.getMinecraftUsername());
        newsDTO.setName("test-article-name-" + UUID.randomUUID());
        newsDTO.setTitle("Test Article Title " + UUID.randomUUID());
        newsDTO.setSubtitle("Test Article Sub title " + UUID.randomUUID());
        newsDTO.setBodyHtml("<h1>Test Body Html" + UUID.randomUUID() + "</h1>");
        newsDTO.setBodyMarkdown("# Test Body Html" + UUID.randomUUID());
        newsDTO.setDraft(true);
        return newsDTO;
    }

    private PacifistaNewsDTO sendCreateRequest(final PacifistaNewsDTO request, boolean needToFail) throws Exception {
        final MockMultipartFile newsImage = new MockMultipartFile(
                "image",
                "testfile-from-photoshop.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                ("%?x~?X?????rnaAr7| x??<@????#????<O?????悷?D???M?\u07B2?J??]??}??#(z??RR?4?MȢ???<BncND?ԤbE???He29??????t)??r?\n" +
                                        "e??]?u??@'?)I?m?O??S????܀b???r?'?ߩ???k?N?5?X=!  ???*?⸔??00????Z??B???????>??X?F.P?g,?O?7??\"?c&?G۰Y?ݴkX?W?v??><??o?7S$?H?????a?WgN/J^XE?kVL\"'?x?j?kM???h?O????1%?Jm??Z???O?7p??yx??LAx??\"_q[?aCjU???l\n" +
                                        "?n???:c???M?v?C?km[????`2?u??k ??f?xT?D?,d??i#7??ų?h6?5?\n" +
                                        "                                   @?$2?p?q?bG??&?9(%?=b??y???l???w?C??B?V??4?R?B? ?q??n??s?W-w??:?6?r'&f?l??90I?a??? ?ٍ?[1??|?͏a?\n" +
                                        "                                                        `f1???}??{?+? ?3f?~fY??j>???nW?+?ݛ?^ah?F??|3f[?/6o?lܲ?ah???C?훑????7˗??(??n?????~????~?OB??f?~a?v????<?ǡ?v??/0.?ٹ?xϚ?A???????;f䗘Z?Q?A????????7#?2!\\{?s??>?K?ڜ?Xz???g?ސ諞>l.?X??rqrA]?%??-c/'Ɨ?e?m??'?K???r?6?QC9                                                                                                                                                F~?z/qپ[?7???-???<?h?/`??T?????W?y?S?z/q??o???z???:?>?{??/???-???Gb?'??=5*??zH=Z??-??!????)???t8Ǚy\n" +
                                        "???h??7?j???-?CQ?Z??3???,?YΡ7??????\n" +
                                        "??a?,5dT_(?;ݝ]?OQ\\#u?,5d?ko?/S㡥U?K\n" +
                                        "~?uw?M??????~????????uK???_?Y\\??3(\\?I?c??X]?o?????_??*???W?Af??ԛuI?Q?+?}???Z??au??Mo??/q;?R[?k?O?*???~6͢:k??Tktk^?׆??֜?&???[\n" +
                                        "?VJO???E??xw3Bx???SK?V?uA?;V?at$??[?jj??h?XFZS|(?v=>???j;?֊?븫Q?]>.??w?#?\\???a??#?~7??q#)??r?3?6<??X???,g,?&5???eNyd?l?ԓ???l͊ٴMȡ?f??AH6Mb??c&b?Qd?b٬]Y?X?k??3?>?C?S??\n" +
                                        "??K??E????#?^??B?)??f2?\\?,(?+]ѳʶV;V*?Ꜯ\tڋAS^??_O??~?i??{P?^h???\t-??]???s???<?q??{?g???͞[rR???r?nD??ayo?7^??_???:_?:_???c?9b?R?$?;??~Y??????Z?(?y?k?f?'`l?Y?c\n" +
                                        "          ^AH5d\\W???DWF?k]?+Q???7c5???").getBytes()
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
        final MockMultipartFile newsImage = new MockMultipartFile(
                "image",
                "testfile-from-photoshop2.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                ("%?x~?X?????rnaAr7| x??<@????#????<O?????悷?D???M?\u07B2?J??]??}??#(z??RR?4?MȢ???<BncND?ԤbE???He29??????t)??r?\n" +
                        "e??]?u??@'?)I?m?O??S????܀b???r?'?ߩ???k?N?5?X=!  ???*?⸔??00????Z??B???????>??X?F.P?g,?O?7??\"?c&?G۰Y?ݴkX?W?v??><??o?7S$?H?????a?WgN/J^XE?kVL\"'?x?j?kM???h?O????1%?Jm??Z???O?7p??yx??LAx??\"_q[?aCjU???l\n" +
                        "?n???:c???M?v?C?km[????`2?u??k ??f?xT?D?,d??i#7??ų?h6?5?\n" +
                        "                                   @?$2?p?q?bG??&?9(%?=b??y???l???w?C??B?V??4?R?B? ?q??n??s?W-w??:?6?r'&f?l??90I?a??? ?ٍ?[1??|?͏a?\n" +
                        "                                                        `f1???}??{?+? ?3f?~fY??j>???nW?+?ݛ?^ah?F??|3f[?/6o?lܲ?ah???C?훑????7˗??(??n?????~????~?OB??f?~a?v????<?ǡ?v??/0.?ٹ?xϚ?A???????;f䗘Z?Q?A????????7#?2!\\{?s??>?K?ڜ?Xz???g?ސ諞>l.?X??rqrA]?%??-c/'Ɨ?e?m??'?K???r?6?QC9                                                                                                                                                F~?z/qپ[?7???-???<?h?/`??T?????W?y?S?z/q??o???z???:?>?{??/???-???Gb?'??=5*??zH=Z??-??!????)???t8Ǚy\n" +
                        "???h??7?j???-?CQ?Z??3???,?YΡ7??????\n" +
                        "??a?,5dT_(?;ݝ]?OQ\\#u?,5d?ko?/S㡥U?K\n" +
                        "~?uw?M??????~????????uK???_?Y\\??3(\\?I?c??X]?o?????_??*???W?Af??ԛuI?Q?+?}???Z??au??Mo??/q;?R[?k?O?*???~6͢:k??Tktk^?׆??֜?&???[\n" +
                        "?VJO???E??xw3Bx???SK?V?uA?;V?at$??[?jj??h?XFZS|(?v=>???j;?֊?븫Q?]>.??w?#?\\???a??#?~7??q#)??r?3?6<??X???,g,?&5???eNyd?l?ԓ???l͊ٴMȡ?f??AH6Mb??c&b?Qd?b٬]Y?X?k??3?>?C?S??\n" +
                        "??K??E????#?^??B?)??f2?\\?,(?+]ѳʶV;V*?Ꜯ\tڋAS^??_O??~?i??{P?^h???\t-??]???s???<?q??{?g???͞[rR???r?nD??ayo?7^??_???:_?:_???c?9b?R?$?;??~Y??????Z?(?y?k?f?'`l?Y?c\n" +
                        "          ^AH5d\\W???DWF?k]?+Q???7c5???").getBytes()
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
            this.mockMvc.perform(get(BASE_ROUTE + "?id=" + newsId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().is4xxClientError());
            return;
        }

        this.mockMvc.perform(get(BASE_ROUTE + "?id=" + newsId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        this.mockMvc.perform(delete(BASE_ROUTE + "?id=" + newsId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    private PageDTO<PacifistaNewsDTO> getAll(final int page) throws Exception {
        final Type gsonType = new TypeToken<PageDTO<PacifistaNewsDTO>>() {}.getType();

        MvcResult result = this.mockMvc.perform(get(BASE_ROUTE)
                .param("page", Integer.toString(page))
                .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        return this.jsonHelper.fromJson(result.getResponse().getContentAsString(), gsonType);
    }

    private PacifistaNewsDTO getNewsById(final UUID newsId, boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(get(BASE_ROUTE + "/" + newsId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().is4xxClientError());
            return null;
        }

        MvcResult result = this.mockMvc.perform(get(BASE_ROUTE + "/" + newsId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        return this.jsonHelper.fromJson(result.getResponse().getContentAsString(), PacifistaNewsDTO.class);
    }

    private void testGetImageSuccess(final UUID imageId, boolean needToFail) throws Exception {
        if (needToFail) {
            this.mockMvc.perform(get(BASE_ROUTE + "/file/" + imageId)).andExpect(status().is4xxClientError());
        } else {
            this.mockMvc.perform(get(BASE_ROUTE + "/file/" + imageId)).andExpect(status().isOk());
        }
    }

    private PageDTO<PacifistaNewsLikeDTO> getLikes(final UUID newsId, final int page) throws Exception {
        final Type gsonType = new TypeToken<PageDTO<PacifistaNewsLikeDTO>>() {}.getType();

        MvcResult result = this.mockMvc.perform(get(LIKES_ROUTE + "/" + newsId)
                .param("page", Integer.toString(page))
                .header(HttpHeaders.AUTHORIZATION, "Bearer dd" + UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

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

}
