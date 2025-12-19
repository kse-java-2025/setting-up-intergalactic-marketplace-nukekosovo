package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.OrderItem;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.OrderItemDTO;
import com.cosmocats.marketplace.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemMapperTest {

    @InjectMocks
    private OrderItemMapperImpl orderItemMapper;

    @Mock
    private ProductMapper productMapper;

    @Test
    void toDTO_shouldMapCorrectly() {
        // Updated constructor to 4 arguments (id, product, quantity, price)
        OrderItem entity = new OrderItem(1L, new Product(), 2L, BigDecimal.TEN);
        when(productMapper.toProductDTO(any())).thenReturn(new ProductDTO());

        OrderItemDTO dto = orderItemMapper.toOrderItemDTO(entity);

        assertNotNull(dto);
        assertEquals(2L, dto.getQuantity());
        assertEquals(BigDecimal.TEN, dto.getPrice());
        assertNotNull(dto.getProduct());
    }

    @Test
    void toEntity_shouldMapCorrectly() {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setQuantity(2L);
        dto.setPrice(BigDecimal.TEN);
        dto.setProduct(new ProductDTO());

        when(productMapper.toProduct(any(ProductDTO.class))).thenReturn(new Product());

        OrderItem entity = orderItemMapper.toOrderItem(dto);

        assertNotNull(entity);
        assertEquals(2L, entity.getQuantity());
        assertEquals(BigDecimal.TEN, entity.getPrice());
        assertNotNull(entity.getProduct());
    }
}