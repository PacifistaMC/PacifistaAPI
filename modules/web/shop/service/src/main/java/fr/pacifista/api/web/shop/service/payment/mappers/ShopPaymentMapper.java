package fr.pacifista.api.web.shop.service.payment.mappers;

import com.funixproductions.core.crud.mappers.ApiMapper;
import fr.pacifista.api.web.shop.client.payment.dtos.PacifistaShopPaymentResponseDTO;
import fr.pacifista.api.web.shop.service.payment.entities.ShopPayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShopPaymentMapper extends ApiMapper<ShopPayment, PacifistaShopPaymentResponseDTO> {

    @Override
    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "urlClientRedirection", ignore = true)
    @Mapping(target = "orderPaid", ignore = true)
    PacifistaShopPaymentResponseDTO toDto(ShopPayment entity);

}
