package com.cosmocats.marketplace.mapper;

import com.cosmocats.marketplace.domain.Cart;
import com.cosmocats.marketplace.dto.CartDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    CartDTO toDTO(Cart cart);

    Cart toEntity(CartDTO cartDTO);
}