package com.epam.esm.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Objects;

public class CartItem {

    private long idCertificate;
    @DecimalMin(value = "1", message = "Enter count of certificates")
    @DecimalMax(value = "10", message = "Exceeding the maximum quantity 10")
    private int count;
    private BigDecimal priceOfCertificate;

    public CartItem(long idCertificate, int count, BigDecimal priceOfCertificate) {
        this.idCertificate = idCertificate;
        this.count = count;
        this.priceOfCertificate = priceOfCertificate;
    }

    public CartItem() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getIdCertificate() {
        return idCertificate;
    }

    public void setIdCertificate(long idCertificate) {
        this.idCertificate = idCertificate;
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
        CartItem cartItem = (CartItem) o;
        return idCertificate == cartItem.idCertificate && count == cartItem.count && Objects.equals(priceOfCertificate, cartItem.priceOfCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCertificate, count, priceOfCertificate);
    }
}
