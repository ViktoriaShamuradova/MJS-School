package com.epam.esm.entity;

import com.epam.esm.dto.CartItem;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "order_items")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"certificate", "order"})
@Getter
@Setter
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
        priceOfCertificate = cartItem.getPriceOfCertificate();
    }

  }
