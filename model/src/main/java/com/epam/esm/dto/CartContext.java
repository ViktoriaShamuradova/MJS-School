package com.epam.esm.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CartContext {

    private Set<CartItem> cartItems;
    private long userId;

    public CartContext() {
        cartItems = new HashSet<>();
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartContext cart = (CartContext) o;
        return userId == cart.userId && Objects.equals(cartItems, cart.cartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItems, userId);
    }
}
