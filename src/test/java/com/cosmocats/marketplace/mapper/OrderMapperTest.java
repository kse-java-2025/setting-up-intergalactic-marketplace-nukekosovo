package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Order;
import com.cosmocats.marketplace.domain.OrderItem;
import com.cosmocats.marketplace.dto.OrderDTO;
import com.cosmocats.marketplace.dto.OrderItemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    @InjectMocks
    private OrderMapperImpl orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @Test
    void toDTO_shouldMapOrderAndItems() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderItems(Collections.singletonList(new OrderItem()));

        when(orderItemMapper.toOrderItemDTO(any())).thenReturn(new OrderItemDTO());

        OrderDTO dto = orderMapper.toOrderDTO(order);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertFalse(dto.getOrderItems().isEmpty());
    }

    @Test
    void toEntity_shouldMapOrderAndItems() {
        OrderDTO dto = new OrderDTO();
        dto.setId(1L);
        dto.setOrderItems(Collections.singletonList(new OrderItemDTO()));

        when(orderItemMapper.toOrderItem((OrderItemDTO) any())).thenReturn(new OrderItem());

        Order entity = orderMapper.toOrder(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertFalse(entity.getOrderItems().isEmpty());
    }
}