package com.cosmocats.marketplace.mapper;
import com.cosmocats.marketplace.domain.OrderItem;
import com.cosmocats.marketplace.dto.OrderItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {
    OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItem(OrderItemDTO orderItemDTO);
}
