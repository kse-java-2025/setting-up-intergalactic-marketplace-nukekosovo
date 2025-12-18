package com.cosmocats.marketplace.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {

    private Long id;

    private Long userId;

    @Valid
    @NotEmpty
    private List<CartItemDTO> cartItems;
}
