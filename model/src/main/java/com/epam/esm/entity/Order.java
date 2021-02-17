package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

public class Order {
    private long id;
    private long userId;
    private BigDecimal totalSum;
    private Integer count;
    private Instant createDate;

    public Order() {
    }

    public Order(long userId, Set<OrderItem> orderItems) {
        this.userId = userId;
        createDate = Instant.now();
        calcSumAndCount(orderItems);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer totalCount) {
        this.count = totalCount;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && userId == order.userId && Objects.equals(totalSum, order.totalSum) && Objects.equals(count, order.count) && Objects.equals(createDate, order.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, totalSum, count, createDate);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalSum=" + totalSum +
                ", totalCount=" + count +
                ", createDate=" + createDate +
                '}';
    }

    private void calcSumAndCount(Set<OrderItem> orderItems) {
        count = 0;
        totalSum = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
            count = count + item.getCount();
            totalSum = totalSum.add(item.getPriceOfCertificate().multiply(BigDecimal.valueOf(item.getCount())));
        }
    }
}
