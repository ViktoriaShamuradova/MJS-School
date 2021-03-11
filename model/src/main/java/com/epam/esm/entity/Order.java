package com.epam.esm.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"orderItems", "user"})
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

    public Order(long userId) {
        super();
        this.user = new User();
        user.setId(userId);
        createDate = Instant.now();
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

}
