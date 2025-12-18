package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Order;
import com.cosmocats.marketplace.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "status", target = "orderStatus")
    OrderDTO toOrderDTO(Order order);

    @Mapping(source = "orderStatus", target = "status")
    @Mapping(target = "businessKey", ignore = true)
    Order toOrder(OrderDTO orderDTO);
}