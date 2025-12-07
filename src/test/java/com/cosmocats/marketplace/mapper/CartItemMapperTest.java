package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.CartItem;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.CartItemDTO;
import com.cosmocats.marketplace.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemMapperTest {

    private CartItemMapper cartItemMapper;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        cartItemMapper = Mappers.getMapper(CartItemMapper.class);
        ReflectionTestUtils.setField(cartItemMapper, "productMapper", productMapper);
    }

    @Test
    void toDTO_shouldMapCorrectly() {
        Product product = new Product();
        product.setId(1L);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(5L);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);

        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        // Act
        CartItemDTO dto = cartItemMapper.toDTO(cartItem);

        // Assert
        assertNotNull(dto);
        assertEquals(5L, dto.getQuantity());
        assertEquals(1L, dto.getProduct().getId());
    }

    @Test
    void toEntity_shouldMapCorrectly() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);

        CartItemDTO dto = new CartItemDTO();
        dto.setProduct(productDTO);
        dto.setQuantity(10L);

        Product product = new Product();
        product.setId(1L);

        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(product);

        // Act
        CartItem entity = cartItemMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(10L, entity.getQuantity());
        assertEquals(1L, entity.getProduct().getId());
    }
}