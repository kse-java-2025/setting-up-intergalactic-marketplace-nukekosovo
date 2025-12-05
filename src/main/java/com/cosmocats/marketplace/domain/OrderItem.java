package com.cosmocats.marketplace.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private Long id;
    private Product product;
    private Long quantity;
    private BigDecimal price;
}