package com.epam.esm.entity;

import com.epam.esm.dto.CartItem;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {

    private long id;
    private long certificateId;
    private int count;
    private long orderId;
    private BigDecimal priceOfCertificate;

    public OrderItem() {
    }

    public OrderItem(CartItem cartItem) {
        certificateId = cartItem.getCertificateDTO().getId();
        count=cartItem.getCount();
        priceOfCertificate=cartItem.getCertificateDTO().getPrice();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(long certificateId) {
        this.certificateId = certificateId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPriceOfCertificate() {
        return priceOfCertificate;
    }

    public void setPriceOfCertificate(BigDecimal priceOfCertificate) {
        this.priceOfCertificate = priceOfCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return id == orderItem.id && certificateId == orderItem.certificateId && count == orderItem.count && orderId == orderItem.orderId && Objects.equals(priceOfCertificate, orderItem.priceOfCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, certificateId, count, orderId, priceOfCertificate);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", certificateId=" + certificateId +
                ", count=" + count +
                ", orderId=" + orderId +
                ", priceOfCertificate=" + priceOfCertificate +
                '}';
    }
}
