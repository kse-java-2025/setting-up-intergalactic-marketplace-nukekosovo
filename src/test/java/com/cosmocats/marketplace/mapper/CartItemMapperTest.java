package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.CartItem;
import com.cosmocats.marketplace.domain.Product;
import com.cosmocats.marketplace.dto.CartItemDTO;
import com.cosmocats.marketplace.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemMapperTest {

    @InjectMocks
    private CartItemMapperImpl cartItemMapper;

    @Mock
    private ProductMapper productMapper;

    @Test
    void toDTO_shouldMapCorrectly() {
        Product product = new Product();
        product.setId(1L);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(5L);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);

        when(productMapper.toProductDTO(any(Product.class))).thenReturn(productDTO);

        // Act
        CartItemDTO dto = cartItemMapper.toCartItemDTO(cartItem);

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

        when(productMapper.toProduct(any(ProductDTO.class))).thenReturn(product);

        // Act
        CartItem entity = cartItemMapper.toCartItem(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(10L, entity.getQuantity());
        assertEquals(1L, entity.getProduct().getId());
    }
}