package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.OrderItem;
import com.cosmocats.marketplace.domain.OrderItemEntity;
import com.cosmocats.marketplace.dto.OrderItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {
    // DTO <-> Domain
    OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    @Mapping(target = "product", source = "product")
    OrderItem toOrderItem(OrderItemDTO orderItemDTO);

    // Entity <-> Domain
    @Mapping(target = "order", ignore = true) // Circular reference handling usually needed
    OrderItemEntity toOrderItemEntity(OrderItem orderItem);

    OrderItem toOrderItem(OrderItemEntity orderItemEntity);
}