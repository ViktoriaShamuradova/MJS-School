package com.epam.esm.entity;

import com.epam.esm.dto.CartItem;
import com.epam.esm.listener.GeneralEntityListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"certificate", "order"})
@EntityListeners(GeneralEntityListener.class)
public class OrderItem extends com.epam.esm.entity.Entity<Long> {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_certificate")
    private Certificate certificate;
    @Column(name = "count")
    private int count;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    private Order order;
    @Column(name = "price_certificate")
    private BigDecimal priceOfCertificate;

    public OrderItem(Certificate certificate, int count, Order order, BigDecimal priceOfCertificate) {
        super();
        this.certificate = certificate;
        this.count = count;
        this.order = order;
        this.priceOfCertificate = priceOfCertificate;
    }

    public OrderItem(CartItem cartItem) {
        super();
        this.certificate = new Certificate();
        certificate.setId(cartItem.getIdCertificate());
        count = cartItem.getCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return count == orderItem.count && Objects.equals(certificate, orderItem.certificate) && Objects.equals(priceOfCertificate, orderItem.priceOfCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificate, count, priceOfCertificate);
    }
}
