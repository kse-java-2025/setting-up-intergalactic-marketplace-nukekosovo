package com.cosmocats.marketplace.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private Long userId;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private List<OrderItem> orderItems;
}
