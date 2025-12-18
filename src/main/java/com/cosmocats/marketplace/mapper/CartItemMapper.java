package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.CartItem;
import com.cosmocats.marketplace.dto.CartItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartItemMapper {
    CartItemDTO toCartItemDTO(CartItem cartItem);

    CartItem toCartItem(CartItemDTO cartItemDTO);
}