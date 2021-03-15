package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Data
public class Cart {

    private Set<CartItem> cartItems;
    private long userId;

    public Cart() {
        cartItems = new HashSet<>();
    }
}
