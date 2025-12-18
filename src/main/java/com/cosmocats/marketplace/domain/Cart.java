package com.cosmocats.marketplace.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Long id;
    private Long userId;
    private List<CartItem> cartItems;
}
