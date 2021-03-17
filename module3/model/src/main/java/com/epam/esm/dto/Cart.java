package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Data
public class Cart {

    private Set<CartItem> cartItems;
    @Positive
    @Min(1)
    private long userId;

    public Cart() {
        cartItems = new HashSet<>();
    }
}
