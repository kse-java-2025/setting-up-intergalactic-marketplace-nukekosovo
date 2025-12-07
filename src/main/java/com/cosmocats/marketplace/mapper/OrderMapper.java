package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Order;
import com.cosmocats.marketplace.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "status", target = "orderStatus") // Fix DTO mapping
    OrderDTO toDTO(Order order);

    @Mapping(source = "orderStatus", target = "status") // Fix Entity mapping
    @Mapping(target = "businessKey", ignore = true)     // Ignore internal ID
    Order toEntity(OrderDTO orderDTO);
}