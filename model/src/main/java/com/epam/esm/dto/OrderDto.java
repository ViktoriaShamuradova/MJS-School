package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

public class OrderDto extends RepresentationModel<OrderDto> {

    private long id;
    private Set<OrderItemDto> orderReadItems;
    private BigDecimal totalSum;
    private int totalCount;
    private long userId;
    private Instant createDate;

    public OrderDto() {
    }

    public Set<OrderItemDto> getOrderReadItems() {
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

    public void setOrderReadItems(Set<OrderItemDto> orderReadItems) {
        this.orderReadItems = orderReadItems;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return id == orderDto.id && totalCount == orderDto.totalCount && userId == orderDto.userId && Objects.equals(orderReadItems, orderDto.orderReadItems) && Objects.equals(totalSum, orderDto.totalSum) && Objects.equals(createDate, orderDto.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderReadItems, totalSum, totalCount, userId, createDate);
    }
}
