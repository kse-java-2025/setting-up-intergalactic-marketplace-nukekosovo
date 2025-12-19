package com.cosmocats.marketplace.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private UUID businessKey;
    private Long userId;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private List<OrderItem> orderItems;
}