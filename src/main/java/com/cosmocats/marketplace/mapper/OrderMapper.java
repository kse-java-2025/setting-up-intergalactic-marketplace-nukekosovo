package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Order;
import com.cosmocats.marketplace.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    OrderDTO toOrderDTO(Order order);

    Order toOrder(OrderDTO orderDTO);
}