package com.cosmocats.marketplace.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import lombok.Data;

@Data
public class CartItemDTO {

    @Valid
    private ProductDTO product;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Long quantity;
}