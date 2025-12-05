package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Order;
import com.cosmocats.marketplace.domain.OrderItem;
import com.cosmocats.marketplace.dto.OrderDTO;
import com.cosmocats.marketplace.dto.OrderItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @BeforeEach
    void setUp() {
        orderMapper = Mappers.getMapper(OrderMapper.class);
        ReflectionTestUtils.setField(orderMapper, "orderItemMapper", orderItemMapper);
    }

    @Test
    void toDTO_shouldMapOrderAndItems() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderItems(Collections.singletonList(new OrderItem()));

        when(orderItemMapper.toDTO(any())).thenReturn(new OrderItemDTO());

        OrderDTO dto = orderMapper.toDTO(order);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertFalse(dto.getOrderItems().isEmpty());
    }

    @Test
    void toEntity_shouldMapOrderAndItems() {
        OrderDTO dto = new OrderDTO();
        dto.setId(1L);
        dto.setOrderItems(Collections.singletonList(new OrderItemDTO()));

        when(orderItemMapper.toEntity(any())).thenReturn(new OrderItem());

        Order entity = orderMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertFalse(entity.getOrderItems().isEmpty());
    }
}