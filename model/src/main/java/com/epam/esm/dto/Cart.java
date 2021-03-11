package com.epam.esm.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()
public class Cart {

    private Set<CartItem> cartItems;
    private long userId;

    public Cart() {
        cartItems = new HashSet<>();
    }

}
