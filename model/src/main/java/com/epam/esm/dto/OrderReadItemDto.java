package com.epam.esm.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderReadItemDto {

    private CertificateDTO certificateDTO;
    private int totalCount;
    private BigDecimal totalSum;
    private BigDecimal priceOfCertificateAtTheTimeOfPurchase;

    public OrderReadItemDto(CartItem cartItem) {
        this.certificateDTO = cartItem.getCertificateDTO();
        this.totalCount = cartItem.getCount();
        this.priceOfCertificateAtTheTimeOfPurchase = certificateDTO.getPrice();
        this.totalSum = priceOfCertificateAtTheTimeOfPurchase.multiply(BigDecimal.valueOf(totalCount));
    }
    public OrderReadItemDto(){}

    public CertificateDTO getCertificateDTO() {
        return certificateDTO;
    }

    public void setCertificateDTO(CertificateDTO certificateDTO) {
        this.certificateDTO = certificateDTO;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    public BigDecimal getPriceOfCertificateAtTheTimeOfPurchase() {
        return priceOfCertificateAtTheTimeOfPurchase;
    }

    public void setPriceOfCertificateAtTheTimeOfPurchase(BigDecimal priceOfCertificateAtTheTimeOfPurchase) {
        this.priceOfCertificateAtTheTimeOfPurchase = priceOfCertificateAtTheTimeOfPurchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderReadItemDto that = (OrderReadItemDto) o;
        return totalCount == that.totalCount && Objects.equals(certificateDTO, that.certificateDTO) && Objects.equals(totalSum, that.totalSum) && Objects.equals(priceOfCertificateAtTheTimeOfPurchase, that.priceOfCertificateAtTheTimeOfPurchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificateDTO, totalCount, totalSum, priceOfCertificateAtTheTimeOfPurchase);
    }
}
