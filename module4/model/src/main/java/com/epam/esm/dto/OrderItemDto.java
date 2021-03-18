package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemDto  extends RepresentationModel<OrderDto> {

    @Positive
    @Min(1)
    private long idCertificate;
    @Min(1)
    @Max(10)
    private int totalCount;
    @Digits(integer = 15, fraction = 2)
    @DecimalMin(value = "0", message = "Enter certificate price")
    private BigDecimal priceOfCertificateAtTheTimeOfPurchase;

    public OrderItemDto(long idCertificate, int totalCount, BigDecimal priceOfCertificateAtTheTimeOfPurchase) {
        this.idCertificate = idCertificate;
        this.totalCount = totalCount;
        this.priceOfCertificateAtTheTimeOfPurchase = priceOfCertificateAtTheTimeOfPurchase;
    }
}
