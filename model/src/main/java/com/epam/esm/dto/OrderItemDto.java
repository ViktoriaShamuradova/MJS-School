package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemDto  extends RepresentationModel<OrderDto> {

    private long idCertificate;
    private int totalCount;
    private BigDecimal priceOfCertificateAtTheTimeOfPurchase;

    public OrderItemDto(long idCertificate, int totalCount, BigDecimal priceOfCertificateAtTheTimeOfPurchase) {
        this.idCertificate = idCertificate;
        this.totalCount = totalCount;
        this.priceOfCertificateAtTheTimeOfPurchase = priceOfCertificateAtTheTimeOfPurchase;
    }
}
