package fr.pacifista.api.web.shop.service.articles.resources;

import com.funixproductions.api.user.client.dtos.UserDTO;
import com.funixproductions.api.user.client.security.CurrentSession;
import com.funixproductions.core.crud.dtos.PageDTO;
import com.funixproductions.core.files.ressources.ApiStorageResource;
import com.funixproductions.core.tools.pdf.tools.VATInformation;
import fr.pacifista.api.web.shop.client.articles.clients.ShopArticlesClient;
import fr.pacifista.api.web.shop.client.articles.dtos.ShopArticleDTO;
import fr.pacifista.api.web.shop.service.articles.services.ShopArticleService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/shop/articles")
public class ShopArticleResource extends ApiStorageResource<ShopArticleDTO, ShopArticleService> implements ShopArticlesClient {

    private final CurrentSession currentSession;

    public ShopArticleResource(ShopArticleService service,
                               CurrentSession currentSession) {
        super(service);
        this.currentSession = currentSession;
    }

    @Override
    public PageDTO<ShopArticleDTO> getAll(String page, String elemsPerPage, String search, String sort) {
        final PageDTO<ShopArticleDTO> result = super.getAll(page, elemsPerPage, search, sort);

        setTaxInDto(result.getContent());
        return result;
    }

    @Override
    public ShopArticleDTO findById(String id) {
        final ShopArticleDTO articleDTO = super.findById(id);

        setTaxInDto(List.of(articleDTO));
        return articleDTO;
    }

    @NonNull
    private VATInformation getVat() {
        final UserDTO user = currentSession.getCurrentUser();
        VATInformation vatInformation = VATInformation.FRANCE;

        if (user != null) {
            vatInformation = VATInformation.getVATInformation(user.getCountry().getCountryCode2Chars());
        }

        return vatInformation == null ? VATInformation.FRANCE : vatInformation;
    }

    private void setTaxInDto(final Iterable<ShopArticleDTO> articles) {
        final VATInformation vatInformation = getVat();

        for (ShopArticleDTO articleDTO : articles) {
            articleDTO.setTax(articleDTO.getPrice() * (vatInformation.getVatRate() / 100));
            articleDTO.setPriceWithTax(articleDTO.getPrice() + articleDTO.getTax());
        }
    }
}
