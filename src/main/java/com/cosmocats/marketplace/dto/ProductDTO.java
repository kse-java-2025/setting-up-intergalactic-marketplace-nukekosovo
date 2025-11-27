package com.cosmocats.marketplace.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Valid
    private CategoryDTO category;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Min(value = 0, message = "Quantity cannot be negative")
    private Long quantity;
}