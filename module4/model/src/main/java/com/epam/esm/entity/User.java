package com.epam.esm.entity;

import com.epam.esm.listener.GeneralEntityListener;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"orders"})
@Data
@EntityListeners(GeneralEntityListener.class)
public class User extends com.epam.esm.entity.Entity<Long> {

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private Instant createDate;
    @Column(name = "last_update_date", columnDefinition = "TIMESTAMP")
    private Instant lastUpdateDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Order> orders;

    public User(long id) {
        super(id);
    }

    public void addOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(order);
        order.setUser(this);
    }
}
