package com.epam.esm.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

public class OrderReadDto {

    private long id;
    private Set<OrderReadItemDto> orderReadItems;
    private BigDecimal totalSum;
    private int totalCount;
    private long userId;
    private Instant createDate;

    public OrderReadDto() {

    }

    public Set<OrderReadItemDto> getOrderReadItems() {
        return orderReadItems;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Set<OrderReadItemDto> getCartItems() {
        return orderReadItems;
    }

    public void setOrderReadItems(Set<OrderReadItemDto> orderReadItems) {
        this.orderReadItems = orderReadItems;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderReadDto that = (OrderReadDto) o;
        return totalCount == that.totalCount && userId == that.userId && id == that.id && Objects.equals(orderReadItems, that.orderReadItems) && Objects.equals(totalSum, that.totalSum) && Objects.equals(createDate, that.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderReadItems, totalSum, totalCount, userId, id, createDate);
    }

    //    public void addCartItem(CartItem cartItem) {
//        if (cartItems.containsValue(cartItem)) {
//            CartItem cartItemBeforeUpdate = cartItems.get(cartItem.getCertificateDTO().getId());
//            cartItemBeforeUpdate.setCount(cartItemBeforeUpdate.getCount() + cartItem.getCount());
//            cartItems.put(cartItem.getCertificateDTO().getId(), cartItemBeforeUpdate);
//        } else {
//            cartItems.put(cartItem.getCertificateDTO().getId(), cartItem);
//        }
//        refreshData();
//    }

//
//    private void calcSumAndCount(Cart cart) {
//        totalCount = 0;
//        totalSum = BigDecimal.ZERO;
//        for (CartItem item : cart.getCartItems()) {
//            totalCount = totalCount + item.getCount();
//            totalSum = totalSum.add(item.getCertificateDTO().getPrice().multiply(BigDecimal.valueOf(item.getCount())));
//        }
//    }

}
