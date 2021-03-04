package com.epam.esm.entity;

import com.epam.esm.dto.CartItem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "order_items")
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

    public OrderItem() {
        super();
    }

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
        priceOfCertificate = cartItem.getPriceOfCertificate();
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getPriceOfCertificate() {
        return priceOfCertificate;
    }

    public void setPriceOfCertificate(BigDecimal priceOfCertificate) {
        this.priceOfCertificate = priceOfCertificate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return count == orderItem.count && Objects.equals(certificate, orderItem.certificate) && Objects.equals(order, orderItem.order) && Objects.equals(priceOfCertificate, orderItem.priceOfCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificate, count, priceOfCertificate);
    }
}
