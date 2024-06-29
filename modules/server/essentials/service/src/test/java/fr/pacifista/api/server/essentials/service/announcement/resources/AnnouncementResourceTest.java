package fr.pacifista.api.server.essentials.service.announcement.resources;

import com.funixproductions.api.user.client.clients.UserAuthClient;
import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.enums.UserRole;
import com.funixproductions.core.test.beans.JsonHelper;
import fr.pacifista.api.server.essentials.client.announcement.clients.AnnouncementClientImpl;
import fr.pacifista.api.server.essentials.client.announcement.dtos.AnnouncementDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnnouncementResourceTest {

    private final String route = "/" + AnnouncementClientImpl.PATH;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonHelper jsonHelper;

    @MockBean
    private UserAuthClient userAuthClient;

    @BeforeEach
    void setUp() {
        final UserDTO userDTO = UserDTO.generateFakeDataForTestingPurposes();
        userDTO.setRole(UserRole.PACIFISTA_ADMIN);

        when(userAuthClient.current(anyString())).thenReturn(userDTO);
    }

    @Test
    void testCrud() throws Exception {
        final AnnouncementDTO announcementDTO = new AnnouncementDTO(
                "SpringBootTest",
                "J'annonce que je suis en train de tester le module SpringBootTest. Super genial !"
        );

        MvcResult result = mockMvc.perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(announcementDTO)))
                .andExpect(status().isOk())
                .andReturn();

        final AnnouncementDTO response = jsonHelper.fromJson(result.getResponse().getContentAsString(), AnnouncementDTO.class);
        assertEquals(announcementDTO.getTitle(), response.getTitle());
        assertEquals(announcementDTO.getMessage(), response.getMessage());

        response.setMessage("En fait c'est pas une annonce mais UNE SUPER ANNONCE ! UNE DINGUERIE !");
        result = mockMvc.perform(patch(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                        .content(jsonHelper.toJson(response)))
                .andExpect(status().isOk()).andReturn();
        final AnnouncementDTO patchedResponse = jsonHelper.fromJson(result.getResponse().getContentAsString(), AnnouncementDTO.class);
        assertEquals(announcementDTO.getTitle(), patchedResponse.getTitle());
        assertEquals(response.getMessage(), patchedResponse.getMessage());
        assertNotEquals(announcementDTO.getMessage(), patchedResponse.getMessage());

        mockMvc.perform(delete(route + "?id=" + response.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk());
    }

}
