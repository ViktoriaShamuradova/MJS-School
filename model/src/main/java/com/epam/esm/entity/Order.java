package com.epam.esm.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "orders")
public class Order extends Entity<Long> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;
    @Column(name = "total_sum")
    private BigDecimal totalSum;
    @Column(name = "count")
    private Integer count;
    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private Instant createDate;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order",
            orphanRemoval = true)
    private Set<OrderItem> orderItems;

    public Order() {
        super();
        createDate = Instant.now();
    }

    public Order(long userId) {
        super();
        this.user = new User();
        user.setId(userId);
        createDate = Instant.now();
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void add(OrderItem orderItem) {
        if (orderItems == null) {
            orderItems = new HashSet<>();
        }
        boolean isExistOrderItem = false;
        for (OrderItem orderItem1 : orderItems) {
            if (orderItem1.getCertificate().getId() == orderItem.getCertificate().getId()) {
                orderItem1.setCount(orderItem1.getCount() + orderItem.getCount());
                isExistOrderItem = true;
            }
        }
        if (!isExistOrderItem) {
            orderItem.setOrder(this);
            orderItems.add(orderItem);
        }
        refreshDataSumAndCount();
    }

    private void refreshDataSumAndCount() {
        count = 0;
        totalSum = BigDecimal.ZERO;
        for (OrderItem item : getOrderItems()) {
            count = count + item.getCount();
            totalSum = totalSum.add(item.getPriceOfCertificate().multiply(BigDecimal.valueOf(item.getCount())));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(user, order.user) && Objects.equals(totalSum, order.totalSum) && Objects.equals(count, order.count) && Objects.equals(createDate, order.createDate) && Objects.equals(orderItems, order.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, totalSum, count, createDate);
    }
}
