package com.cosmocats.marketplace.mapper;
import com.cosmocats.marketplace.domain.OrderItem;
import com.cosmocats.marketplace.dto.OrderItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {
    OrderItemDTO toDTO(OrderItem orderItem);

    OrderItem toEntity(OrderItemDTO orderItemDTO);
}
