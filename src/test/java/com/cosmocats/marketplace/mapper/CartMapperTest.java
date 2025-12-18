package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Cart;
import com.cosmocats.marketplace.domain.CartItem;
import com.cosmocats.marketplace.dto.CartDTO;
import com.cosmocats.marketplace.dto.CartItemDTO;
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
class CartMapperTest {

    @InjectMocks
    private CartMapperImpl cartMapper;

    @Mock
    private CartItemMapper cartItemMapper;

    @Test
    void toDTO_shouldMapCartAndItems() {
        Cart cart = new Cart(1L, 100L, Collections.singletonList(new CartItem()));

        when(cartItemMapper.toCartItemDTO(any(CartItem.class))).thenReturn(new CartItemDTO());

        CartDTO dto = cartMapper.toCartDTO(cart);

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

        when(cartItemMapper.toCartItem(any(CartItemDTO.class))).thenReturn(new CartItem());

        Cart entity = cartMapper.toCart(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(100L, entity.getUserId());
        assertFalse(entity.getCartItems().isEmpty());
    }
}