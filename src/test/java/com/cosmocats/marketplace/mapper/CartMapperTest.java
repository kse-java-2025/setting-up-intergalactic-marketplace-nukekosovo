package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Cart;
import com.cosmocats.marketplace.domain.CartItem;
import com.cosmocats.marketplace.dto.CartDTO;
import com.cosmocats.marketplace.dto.CartItemDTO;
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
class CartMapperTest {

    private CartMapper cartMapper;

    @Mock
    private CartItemMapper cartItemMapper;

    @BeforeEach
    void setUp() {
        cartMapper = Mappers.getMapper(CartMapper.class);
        ReflectionTestUtils.setField(cartMapper, "cartItemMapper", cartItemMapper);
    }

    @Test
    void toDTO_shouldMapCartAndItems() {
        Cart cart = new Cart(1L, 100L, Collections.singletonList(new CartItem()));

        when(cartItemMapper.toDTO(any(CartItem.class))).thenReturn(new CartItemDTO());

        CartDTO dto = cartMapper.toDTO(cart);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(100L, dto.getUserId());
        assertFalse(dto.getCartItems().isEmpty());
    }

    @Test
    void toEntity_shouldMapCartAndItems() {
        CartDTO dto = new CartDTO();
        dto.setId(1L);
        dto.setUserId(100L);
        dto.setCartItems(Collections.singletonList(new CartItemDTO()));

        when(cartItemMapper.toEntity(any(CartItemDTO.class))).thenReturn(new CartItem());

        Cart entity = cartMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(100L, entity.getUserId());
        assertFalse(entity.getCartItems().isEmpty());
    }
}