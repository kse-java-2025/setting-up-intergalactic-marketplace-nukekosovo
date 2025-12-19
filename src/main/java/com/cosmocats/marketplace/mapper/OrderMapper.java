package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Order;
import com.cosmocats.marketplace.domain.OrderEntity;
import com.cosmocats.marketplace.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    // DTO <-> Domain
    @Mapping(source = "status", target = "orderStatus")
    OrderDTO toOrderDTO(Order order);

    @Mapping(source = "orderStatus", target = "status")
    @Mapping(target = "businessKey", ignore = true)
    Order toOrder(OrderDTO orderDTO);

    // Entity <-> Domain
    Order toOrder(OrderEntity orderEntity);
    OrderEntity toOrderEntity(Order order);
}