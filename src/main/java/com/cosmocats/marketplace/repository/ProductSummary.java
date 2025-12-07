package com.cosmocats.marketplace.repository;

import java.math.BigDecimal;

// Projection interface
public interface ProductSummary {
    String getName();
    BigDecimal getPrice();
}