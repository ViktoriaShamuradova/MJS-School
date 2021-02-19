package com.epam.esm.entity;

import com.epam.esm.dto.CartItem;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_certificate")
    private Certificate  certificate;
    @Column(name = "count")
    private int count;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    private Order order;
    @Column(name = "price_certificate")
    private BigDecimal priceOfCertificate;

    public OrderItem() {
    }

    public OrderItem(CartItem cartItem) {
       // certificateId = cartItem.getCertificateDTO().getId();
        count = cartItem.getCount();
        priceOfCertificate = cartItem.getCertificateDTO().getPrice();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return id == orderItem.id && count == orderItem.count && Objects.equals(certificate, orderItem.certificate) && Objects.equals(order, orderItem.order) && Objects.equals(priceOfCertificate, orderItem.priceOfCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, certificate, count, order, priceOfCertificate);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", certificate=" + certificate +
                ", count=" + count +
                ", order=" + order +
                ", priceOfCertificate=" + priceOfCertificate +
                '}';
    }
}
