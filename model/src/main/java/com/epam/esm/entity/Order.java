package com.epam.esm.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="id_user")
    private User user;
    @Column(name = "total_sum")
    private BigDecimal totalSum;
    @Column(name = "count")
    private Integer count;
    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private Instant createDate;
    //если удалить заказ, то будет произведено каскадное удаление, т.е. удалиться и заказ и его составляющие
    //сли удалить оrder_item, то order не удалится, так как оrder_item не имеет связь с order
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderItem> orderItems;

    public Order() {
    }

    public Order(long userId, Set<OrderItem> orderItems) {
       // this.userId = userId;
        createDate = Instant.now();
        calcSumAndCount(orderItems);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(user, order.user) && Objects.equals(totalSum, order.totalSum) && Objects.equals(count, order.count) && Objects.equals(createDate, order.createDate) && Objects.equals(orderItems, order.orderItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, totalSum, count, createDate, orderItems);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", totalSum=" + totalSum +
                ", count=" + count +
                ", createDate=" + createDate +
                ", orderItems=" + orderItems +
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
