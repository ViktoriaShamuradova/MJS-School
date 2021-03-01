package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItemDto  extends RepresentationModel<OrderDto> {

    private long idCertificate;
    private int totalCount;
    private BigDecimal priceOfCertificateAtTheTimeOfPurchase;

    public OrderItemDto(){}

    public OrderItemDto(long idCertificate, int totalCount, BigDecimal priceOfCertificateAtTheTimeOfPurchase) {
        this.idCertificate = idCertificate;
        this.totalCount = totalCount;
        this.priceOfCertificateAtTheTimeOfPurchase = priceOfCertificateAtTheTimeOfPurchase;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getPriceOfCertificateAtTheTimeOfPurchase() {
        return priceOfCertificateAtTheTimeOfPurchase;
    }

    public void setPriceOfCertificateAtTheTimeOfPurchase(BigDecimal priceOfCertificateAtTheTimeOfPurchase) {
        this.priceOfCertificateAtTheTimeOfPurchase = priceOfCertificateAtTheTimeOfPurchase;
    }

    public long getIdCertificate() {
        return idCertificate;
    }

    public void setIdCertificate(long idCertificate) {
        this.idCertificate = idCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDto that = (OrderItemDto) o;
        return idCertificate == that.idCertificate && totalCount == that.totalCount && Objects.equals(priceOfCertificateAtTheTimeOfPurchase, that.priceOfCertificateAtTheTimeOfPurchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCertificate, totalCount, priceOfCertificateAtTheTimeOfPurchase);
    }
}
