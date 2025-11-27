package com.cosmocats.marketplace.dto;

import com.cosmocats.marketplace.domain.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;

    private Long userId;

    private OrderStatus orderStatus;

    private LocalDateTime createdAt;

    @NotBlank(message = "Shipping address cannot be empty")
    private String shippingAddress;

    private BigDecimal totalPrice;

    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemDTO> orderItems;
}